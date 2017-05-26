package com.ipzoe.light.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.ipzoe.light.bean.entity.*;
import com.ipzoe.light.bean.request.FormulaRequest;
import com.ipzoe.light.bean.response.Code;
import com.ipzoe.light.bean.response.PageBean;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.repository.FormulaRepository;
import com.ipzoe.light.repository.FormulaStageRepository;
import com.ipzoe.light.repository.LightGroupRepository;
import com.ipzoe.light.repository.StageTimeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by cxs on 2017/3/23.
 */
@Service
@Transactional
public class FormulaService {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private FormulaRepository formulaRepository;

    @Autowired
    private FormulaStageRepository formulaStageRepository;

    @Autowired
    private StageTimeRepository stageTimeRepository;

    @Autowired
    private LightGroupRepository lightGroupRepository;


    /**
     * 新增配方
     *
     * @param account        用户
     * @param formulaRequest 配方中包含多个阶段和多个时间段
     * @return
     */
    public Response addFormula(Account account, FormulaRequest formulaRequest) {
        Long accountId = account.getId();
        Formula formula = new Formula();
        List<FormulaStage> formulaStageList = formulaRequest.getFormulaStageList();
        String name = formulaRequest.getName();
        // 校验光配方名字是否正确
        if (StringUtil.isEmpty(name)) {
            return Response.ok(Code.API_FORMULA_NAME_NOT_EXIST);
        }

        // 校验配方阶段数据是否合法
        if (formulaStageHandle(formulaStageList).getCode() != Code.SUCCESS.getCode()) {
            return formulaStageHandle(formulaStageList);
        }
        // 校验时间段数据是否合法
        if (stageTimeHandle(formulaStageList).getCode() != Code.SUCCESS.getCode()) {
            return stageTimeHandle(formulaStageList);
        }
        //  插入配方以获得ID
        formula.setAccountId(accountId);
        formula.setName(name);
        formulaRepository.insertSelective(formula);

        // 插入检查合格的数据
        return handleFormula(formulaStageList, formula);

    }

    /**
     * 处理检查合格的数据
     *
     * @param formulaStageList
     * @param formula
     * @return
     */
    private Response handleFormula(List<FormulaStage> formulaStageList, Formula formula) {

        // 根据配方ID删除以前的配方阶段（不论有没有）
        formulaStageRepository.updateIsDeletedByFormulaId(formula.getId());
        // 根据配方ID删除以前的配方时间段（不论有没有）
        stageTimeRepository.updateIsDeletedByFormulaId(formula.getId());
        // 插入新的数据
        for (FormulaStage formulaStage : formulaStageList) {
            formulaStage.setFormulaId(formula.getId());
            formulaStageRepository.insertSelective(formulaStage);
            for (StageTime stageTime : formulaStage.getStageTimeList()) {
                stageTime.setStageId(formulaStage.getId());
                stageTimeRepository.insertSelective(stageTime);
            }
        }
        return Response.ok();

    }


    /**
     * 配方阶段数据校验处理
     *
     * @param formulaStageList
     * @return
     */
    private Response formulaStageHandle(List<FormulaStage> formulaStageList) {
        // 校验周期数不能重复
        int a = 0;
        int b = 0;
        for (FormulaStage formulaStage : formulaStageList) {
            ++a;
            for (FormulaStage formulaStageInner : formulaStageList) {
                ++b;
                if (a == b) {
                    continue;
                }
                if (formulaStage.getSerialNumber().equals(formulaStageInner.getSerialNumber())) {
                    return Response.ok(Code.ADMIN_FORMULA_PERIOD_CANOT_REPETITION);
                }
            }
            b = 0;
        }

        // 校验持续天数不能等于小于0
        for (FormulaStage formulaStage : formulaStageList) {
            if (formulaStage.getDays() <= 0) {
                return Response.ok(Code.ADMIN_DAYS_CANOT_SMALL_ZERO);
            }
        }
        return Response.ok();
    }

    /**
     * 处理时间段检验
     *
     * @param formulaStageList
     * @return
     */
    private Response stageTimeHandle(List<FormulaStage> formulaStageList) {
        for (FormulaStage formulaStage : formulaStageList) {
            List<StageTime> stageTimeList = formulaStage.getStageTimeList();
            // 校验时间段数量不能大于10
            if (!stageTimeList.isEmpty() && stageTimeList.size() > 10) {
                return Response.ok(Code.API_STAGE_NUMBER_CAN_NOT_BIG_TEN);
            }
            for (StageTime stageTime : stageTimeList) {
                // 校验时间段开始时间不能大于结束时间
                if (stageTime.getEndTime().compareTo(stageTime.getStartTime()) <= 0) {
                    return Response.ok(Code.API_ENDTIME_CANOT_BIG_STARTTIME);
                }
                // 校验时间不能重叠
                if (checkStageTimeRepetition(stageTimeList)) {
                    return Response.ok(Code.API_TIME_CANOT_REPETITION);
                }
            }
        }
        return Response.ok();
    }

    /**
     * 校验时间是否重叠
     *
     * @param stageTimeList
     * @return
     */
    private boolean checkStageTimeRepetition(List<StageTime> stageTimeList) {
        int a = 0;
        int b = 0;
        for (StageTime stageTimeOut : stageTimeList) {
            // 校验时间不能重叠
            LocalTime st = stageTimeOut.getStartTime();
            LocalTime et = stageTimeOut.getEndTime();
            ++a;
            for (StageTime stageTimeInner : stageTimeList) {
                ++b;
                if (a == b) {
                    continue;
                }
                if (st.compareTo(stageTimeInner.getStartTime()) >= 0 && st.compareTo(stageTimeInner.getEndTime()) <= 0 ||
                        et.compareTo(stageTimeInner.getStartTime()) >= 0 && et.compareTo(stageTimeInner.getEndTime()) <= 0 ||
                        st.compareTo(stageTimeInner.getStartTime()) <= 0 && et.compareTo(stageTimeInner.getEndTime()) >= 0) {
                    return true;
                }

            }
            b = 0;

        }

        return false;
    }


    /**
     * 删除配方
     *
     * @param account
     * @param id
     * @return
     */
    public Response deleteFormula(Account account, Long id) {
        // 校验配方是否存在
        Formula formula = formulaRepository.selectByPrimaryKey(id);
        if (formula == null) {
            return Response.ok(Code.API_FORMULA_NOT_EXIST);
        }
        // 校验配方所属ID是否正确
        if (!account.getId().equals(formula.getAccountId())) {
            return Response.ok(Code.API_ACCOUNT_CAN_NOT_DELETE);
        }
        // 校验是否正在使用
        if (checkFormulaStatus(formula)) {
            return Response.ok(Code.API_FORMULA_USEING);
        }

        formula.setIsDeleted(BaseEntity.DELETE_YES);
        // 删除配方ID下所有配方阶段
        formulaStageRepository.setIsDeleted(formula.getId());
        // 删除配方ID下所有阶段时间
        stageTimeRepository.updateIsDeletedByFormulaId(formula.getId());
        // 将包含此配方ID的灯组中的formula_id设置为null
        lightGroupRepository.updateIsNullByFormulaId(formula.getId());
        // 最后删除配方
        formulaRepository.updateByPrimaryKeySelective(formula);
        return Response.ok();

    }

    /**
     * 更新配方
     *
     * @param account
     * @param formulaRequest
     * @return
     */
    public Response updateFormula(Account account, FormulaRequest formulaRequest) {

        Long formulaId = formulaRequest.getFormulaId();
        String name = formulaRequest.getName();
        List<FormulaStage> formulaStageList = formulaRequest.getFormulaStageList();

        Formula formulaDb = formulaRepository.selectByPrimaryKey(formulaId);
        // 校验配方是否存在
        if (formulaDb == null || formulaDb.getIsDeleted().equals(BaseEntity.DELETE_YES)) {
            return Response.ok(Code.API_FORMULA_NAME_NOT_EXIST);
        }
        // 校验配方所属ID是否正确
        if (!account.getId().equals(formulaDb.getAccountId())) {
            return Response.ok(Code.API_FORMULA_ID_NOT_CORRECT);
        }
        // 校验配方是否正在使用
        if (checkFormulaStatus(formulaDb)) {
            return Response.ok(Code.API_FORMULA_USEING);
        }
        // 校验阶段数据是否合法
        if (formulaStageHandle(formulaStageList).getCode() != Code.SUCCESS.getCode()) {
            return formulaStageHandle(formulaStageList);
        }
        // 校验时间段数据是否合法
        if (stageTimeHandle(formulaStageList).getCode() != Code.SUCCESS.getCode()) {
            return stageTimeHandle(formulaStageList);
        }
        formulaDb.setName(name);
        formulaRepository.updateByPrimaryKeySelective(formulaDb);

        // 处理检查合格的数据
        return handleFormula(formulaStageList, formulaDb);

    }

    /**
     * 查询配方
     *
     * @param account 用户
     * @param key     关键字
     * @param type    查询类型1=平台，2=自己
     * @return
     */
    public Response selectFormula(Account account, String key, int type) {
        Map<String, Object> map = new HashMap<>();
        Long myAccountId = account.getId();
        switch (type) {
            case 1:
                map.put("accountPlatform", Account.ACCOUNT_PLATFORM_ID);
                break;
            case 2:
                map.put("myself", myAccountId);
                break;
            default:
                return Response.ok(Code.API_NUMBER_FILED);
        }

        if (key != null) {
            map.put("key", "%" + key + "%");
        }

        List<Formula> formulaList = formulaRepository.selectByAccountId(map);
        return Response.ok(formulaList);

    }

    /**
     * 检查配方是否在使用
     *
     * @param formula
     * @return
     */
    private boolean checkFormulaStatus(Formula formula) {
        List<Formula> formulaDb = formulaRepository.checkFormulaStatus(formula);
        // 配方不在使用
        if (formulaDb.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 后台添加光配方
     *
     * @param formulaRequest
     * @return
     */
    public Response adminAddFormula(FormulaRequest formulaRequest) {

        Formula formula = new Formula();
        List<FormulaStage> formulaStageList = formulaRequest.getFormulaStageList();
        String name = formulaRequest.getName();
        // 校验光配方名字是否存在
        if (StringUtil.isEmpty(name)) {
            return Response.ok(Code.API_FORMULA_NAME_NOT_EXIST);
        }
        formula.setAccountId(0L);
        formula.setName(name);

        // 校验配方阶段数据是否合法
        if (formulaStageHandle(formulaStageList).getCode() != Code.SUCCESS.getCode()) {
            return formulaStageHandle(formulaStageList);
        }
        // 校验时间段数据是否合法
        if (stageTimeHandle(formulaStageList).getCode() != Code.SUCCESS.getCode()) {
            return stageTimeHandle(formulaStageList);
        }
        formulaRepository.insertSelective(formula);
        // 处理检查合格的数据
        return handleFormula(formulaStageList, formula);

    }

    /**
     * 后台删除不在使用中的配方
     *
     * @param formulaId
     * @return
     */
    public Response adminDeleteFormula(Long formulaId) {
        // 校验给定ID是否存在
        Formula formulaDb = formulaRepository.selectByPrimaryKey(formulaId);
        if (formulaDb == null) {
            return Response.ok(Code.ADMIN_FORMULA_NOT_EXIST);
        }
        // 校验配方是否在使用中
        if (checkFormulaStatus(formulaDb)) {
            return Response.ok(Code.ADMIN_ALREADY_USE);
        }
        // 校验配方所属ID是否正确
        if (!formulaDb.getAccountId().equals(Account.ACCOUNT_PLATFORM_ID)) {
            return Response.ok(Code.ADMIN_FORMULA_OF_ACCOUNT_ID_FIELD);
        }

        formulaDb.setIsDeleted(BaseEntity.DELETE_YES);
        // 删除配方所属阶段时间
        List<FormulaStage> formulaStageList = formulaStageRepository.queryByFormulaId(formulaDb.getId());
        formulaStageRepository.setIsDeleted(formulaDb.getId());
        // 删除配方所属阶段下的时间段
        for (FormulaStage formulaStage : formulaStageList) {
            stageTimeRepository.setIsDeleted(formulaStage.getId());
        }
        formulaRepository.updateByPrimaryKeySelective(formulaDb);
        return Response.ok();
    }

    /**
     * 后台更新配方
     *
     * @param formulaRequest
     * @return
     */
    public Response adminUpdateFormula(FormulaRequest formulaRequest) {
        Long formulaId = formulaRequest.getFormulaId();
        String name = formulaRequest.getName();
        List<FormulaStage> formulaStageList = formulaRequest.getFormulaStageList();

        Formula formulaDb = formulaRepository.selectByPrimaryKey(formulaId);
        // 校验配方是否存在
        if (formulaDb == null || formulaDb.getIsDeleted().equals(BaseEntity.DELETE_YES)) {
            return Response.ok(Code.API_FORMULA_NAME_NOT_EXIST);
        }
        // 校验配方所属ID是否正确
        if (0 != (formulaDb.getAccountId())) {
            return Response.ok(Code.API_FORMULA_ID_NOT_CORRECT);
        }
        // 校验配方是否正在使用
        if (checkFormulaStatus(formulaDb)) {
            return Response.ok(Code.API_FORMULA_USEING);
        }
        // 校验阶段数据是否合法
        if (formulaStageHandle(formulaStageList).getCode() != Code.SUCCESS.getCode()) {
            return formulaStageHandle(formulaStageList);
        }
        // 校验时间段数据是否合法
        // 在执行此方法前删除此配方下所有阶段和时间段
        if (stageTimeHandle(formulaStageList).getCode() != Code.SUCCESS.getCode()) {
            return stageTimeHandle(formulaStageList);
        }
        formulaDb.setName(name);
        formulaRepository.updateByPrimaryKeySelective(formulaDb);

        // 处理检查合格的数据
        return handleFormula(formulaStageList, formulaDb);
    }

    /**
     * 后台根据给定的用户ID（官方ID=0）查询配方
     *
     * @param page
     * @param size
     * @param key
     * @param accountId
     * @return
     */
    public Response adminSelectFormula(int page, int size, String key, Long accountId) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", accountId);
        if (key != null) {
            map.put("key", "%" + key + "%");
        }
        PageHelper.startPage(page, size);
        List<Formula> list = formulaRepository.adminSelectByAccountId(map);
        return Response.ok(new PageBean<>(list, formulaRepository.selectCount(null)));
    }


    /**
     * 根据配方ID查询
     *
     * @param id
     * @return
     */
    public Response<Formula> selectByPrimaryKey(Long id) {

        return new Response<>(formulaRepository.selectById(id));
    }
}
