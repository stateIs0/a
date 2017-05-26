package com.ipzoe.light.service;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.entity.SmsRecord;
import com.ipzoe.light.bean.response.Code;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.repository.AccountRepository;
import com.ipzoe.light.repository.SmsRecordRepository;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wzx on 2017/3/24.
 */
@Service
@Transactional
public class AccountService {

    static Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Value("${shiyuan.account}")
    private String shiyuanAccount;

    @Value("${shiyuan.password}")
    private String shiyuanPassword;

    @Value("${shiyuan.extno}")
    private String shiyuanExtNo;

    @Value("${shiyuan.text}")
    private String smsTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SmsRecordRepository smsRecordRepository;

   /* @Autowired
    private SmsService smsService;*/



    /**
     * 用户注册
     * @param username
     * @param password
     * @param code
     * @return
     */
    public Response register(String username,String password,String code) {

        //手机号码重复验证
        Account account = accountRepository.findByUsername(username);
        if (account != null)
            return Response.ok(Code.API_ACCOUNT_SMS_USERNAME_EXIST);

        //验证码校验
        String lastCode = smsRecordRepository.findCodeByPhone(username);
        if (!code.equals(lastCode)) {
            return Response.ok(Code.API_ACCOUNT_SMS_CODE_ERROR);
        }

        Account accountNew = new Account();
        accountNew.setUsername(username);
        accountNew.setPassword(password);
        accountRepository.insertSelective(accountNew);

        return Response.ok();
    }


    /**
     * 完善用户信息
     * @param account
     * @param avatar
     * @param nickname
     * @param enterprise
     * @param email
     * @return
     */
    public Response insert(Account account,String avatar,String nickname,String enterprise,String address,String email) {

        account.setId(account.getId());
        account.setAvatar(avatar);
        account.setNickname(nickname);
        account.setEnterprise(enterprise);
        account.setAddress(address);
        account.setEmail(email);

        accountRepository.updateByPrimaryKey(account);
        return  Response.ok();
    }


    /**
     * 发送短信
     * @param phone
     * @return
     */
    public Response sendSmsCode(String phone){

        //生成6位随机数
        String code = generateSmsCode();

        //调用短信接口
        /*String text = String.format(smsTemplate, code);

        retrofit2.Response<String> response ;
        try {
            response = smsService.singleSend(shiyuanAccount, shiyuanPassword, phone, text, false, shiyuanExtNo)
                    .execute();
            if (!response.isSuccessful()) {
                logger.error("Sms Send Fail! " + response.errorBody().string());
                return Response.ok(Code.API_ACCOUNT_SMS_SEND_ERROR);
            }

            logger.info("Sms Send Success! " + response.body());
        } catch (IOException e) {
            logger.error("Sms Send fail! " + e.toString());
            return Response.ok(Code.API_ACCOUNT_SMS_SEND_ERROR);
        }*/

        //更新数据
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setPhone(phone);
        smsRecord.setCode(code);
        smsRecord.setStatus(SmsRecord.STATUS_NOT_USE);

        smsRecordRepository.insertSelective(smsRecord);
        return Response.ok();
    }

    /**
     * 生成6位数字验证码.
     * <p>
     * 算法: 取当前时间(毫秒数)的后6位, 将其倒置
     *
     * @return
     */
    private String generateSmsCode() {
        long now = new Date().getTime();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; ++i) {
            builder.append(now % 10);
            now /= 10;
        }
        System.out.println(builder.toString());
        return builder.toString();
    }

    /**
     * 修改密码
     * @param phone
     * @param code
     * @param password
     * @param affirm
     * @return
     */
    public Response modifyPassword(String phone, String code, String password, String affirm) {
        Account account = accountRepository.selectByUsername(phone);
        if (account == null) {
            return Response.ok(Code.API_ACCOUNT_NOT_EXIST);
        }
        //todo 校验验证码
        if (!password.equals(affirm)) {
            return Response.ok(Code.API_PASSWORD_NOT_EQUALS);
        }
        return Response.ok();

    }
}
