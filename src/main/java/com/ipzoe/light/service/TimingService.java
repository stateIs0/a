package com.ipzoe.light.service;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.entity.BaseEntity;
import com.ipzoe.light.bean.entity.Timing;
import com.ipzoe.light.bean.request.TimingRequest;
import com.ipzoe.light.bean.response.Code;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.repository.FosterRepository;
import com.ipzoe.light.repository.TimingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

/**
 * Created by cxs on 2017/4/14.
 */
@Service
@Transactional
public class TimingService {


    @Autowired
    private TimingRepository timingRepository;

    @Autowired
    private FosterRepository fosterRepository;

    /**
     * 新增定时
     *
     * @param account
     * @param timingRequest
     * @return
     */
    public Response addTiming(Account account, TimingRequest timingRequest) {

        String name = timingRequest.getName();
        LocalTime startTime = timingRequest.getStartTime();
        LocalTime endTime = timingRequest.getEndTime();
        // 校验时间数据合法性
        if (checkTimeRepetition(account.getId(), startTime, endTime).getCode() != Code.SUCCESS.getCode()) {
            return checkTimeRepetition(account.getId(), startTime, endTime);
        }

        Timing timing = new Timing();
        timing.setAccountId(account.getId());
        timing.setName(name);
        timing.setStartTime(startTime);
        timing.setEndTime(endTime);
        timingRepository.insertSelective(timing);
        return Response.ok();
    }


    /**
     * 校验时间是否重叠，校验开始时间是否大于结束时间
     *
     * @param accountId
     * @param st
     * @param et
     * @return
     */
    private Response checkTimeRepetition(Long accountId, LocalTime st, LocalTime et) {
        // 判断结束时间不能大于开始时间
        if (st.compareTo(et) >= 0) {
            return Response.ok(Code.ADMIN_ENDTIME_CANOT_BIG_STARTTIME);
        }
        List<Timing> timingList = timingRepository.selectByAccountId(accountId);
        for (Timing timing : timingList) {
            if (st.compareTo(timing.getStartTime()) >= 0 && st.compareTo(timing.getEndTime()) <= 0 ||
                    et.compareTo(timing.getStartTime()) >= 0 && et.compareTo(timing.getEndTime()) <= 0 ||
                    st.compareTo(timing.getStartTime()) <= 0 && et.compareTo(timing.getEndTime()) >= 0) {
                return Response.ok(Code.API_FORMULA_STAGE_CAN_NOT_REPETITION);
            }
        }
        return Response.ok();
    }

    /**
     * 更新定时
     *
     * @param account
     * @param timingRequest
     * @return
     */
    public Response update(Account account, TimingRequest timingRequest) {
        String name = timingRequest.getName();
        Long id = timingRequest.getId();
        Long accountId = timingRequest.getAccountId();
        LocalTime startTime = timingRequest.getStartTime();
        LocalTime endTime = timingRequest.getEndTime();

        if (!accountId.equals(account.getId())) {
            return Response.ok(Code.API_TIMING_OF_ACCOUNT_ID_FILED);
        }
        // 校验时间合法性
        if (checkTimeRepetition(accountId, startTime, endTime).getCode() != Code.SUCCESS.getCode()) {
            return checkTimeRepetition(accountId, startTime, endTime);
        }
        Timing timing = timingRepository.selectByPrimaryKey(id);
        timing.setName(name);
        timing.setStartTime(startTime);
        timing.setEndTime(endTime);
        timingRepository.updateByPrimaryKeySelective(timing);
        return Response.ok();
    }

    /**
     * 删除定时
     *
     * @param account
     * @param timingId
     * @return
     */
    public Response deleted(Account account, Long timingId) {
        Timing timing = timingRepository.selectByPrimaryKey(timingId);
        if (!timing.getAccountId().equals(account.getId())) {
            return Response.ok(Code.API_TIMING_OF_ACCOUNT_ID_FILED);
        }
        // 判断与定时绑定的灯组是否在培养中使用(不为空则在使用 则不能删除）
        if (!fosterRepository.checkIsUseById(timingId).isEmpty()) {
            return Response.ok(Code.API_TIMING_USE_ING);
        }
        timing.setIsDeleted(BaseEntity.DELETE_YES);
        timingRepository.updateByPrimaryKeySelective(timing);
        return Response.ok();

    }


}
