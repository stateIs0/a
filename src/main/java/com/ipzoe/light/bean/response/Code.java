package com.ipzoe.light.bean.response;

/**
 * 错误码表.
 * <p>
 * Created by xingfinal on 15/11/28.
 */
public enum Code {
    // 1024以内, 同HTTP
    SUCCESS(200, "Success."),
    UNKNOWN(2000, "未知错误"),
    IO_EXCEPTION(2001, "IO异常"),

    // 10000 ~ 49999, 留给/api使用, 并添加"API_"前缀
    API_ACCOUNT_NOT_EXIST(10001, "用户不存在"),
    API_ACCOUNT_SIGN_IN_ERROR(10002, "登录失败, 请联系管理员"),
    API_ACCOUNT_SMS_SEND_ERROR(10003, "短信发送失败"),
    API_ACCOUNT_SMS_CODE_ERROR(10004, "验证码不正确"),
    API_ACCOUNT_EXIST(10001, "用户已存在"),


    API_FORMULA_USEING(30000, "配方正在使用，不能删除"),
    API_FORMULA_NOT_EXIST(30002, "配方不存在"),
    API_FORMULA_NAME_NOT_EXIST(30003, "配方不存在"),
    API_ENDTIME_CANOT_BIG_STARTTIME(30007, "结束时间不能大于或等于开始时间"),
    API_TIME_CANOT_REPETITION(30008, "时间不能重叠"),
    API_NUMBER_FILED(30013, "输入错误"),
    API_FORMULA_ID_NOT_CORRECT(30014, "配方ID不正确"),


    API_ACCOUNT_LOGIN_PASSWORD_ERROR(10009, "密码错误"),
    API_ACCOUNT_SMS_USERNAME_EXIST(10010, "手机号已存在账户"),


    API_LIGHT_ALREADY_USE(20001, "灯具已配置在灯组中，不能删除，需将灯具从该灯组移除"),
    API_LIGHT_GROUP_ALREADY_EXIST(21001, "灯组名称已存在"),
    API_LIGHT_GROUP_ALREADY_USE(21002, "灯组在启用中"),
    API_LIGHT_GROUP_FORMULA_NOT_EXIST(21003, "灯组未配置光配方"),

    API_FOSTER_NOT_EXIST(22001, "培养已删除"),
    API_FOSTER_IS_FOSTING(22002, "此培养正在培养中"),
    API_FOSTER_IS_CONTINUE(22003, "此培养正在延续培养中"),

    // 50000 ~ 99999, 留给/admin使用, 并添加"ADMIN_"前缀
    ADMIN_NOT_EXIST(51001, "管理员账户不存在"),
    ADMIN_PASSWORD_ERROR(51003, "管理员账户密码错误"),

    ADMIN_FORMULA_NOT_EXIST(51005, "配方不存在"),
    ADMIN_ALREADY_USE(51007, "配方正在使用"),
    ADMIN_FORMULA_OF_ACCOUNT_ID_FIELD(51103, "配方所属ID错误"),
    ADMIN_DAYS_CANOT_SMALL_ZERO(51005, "持续天数不能小于等于零"),
    ADMIN_FORMULA_PERIOD_CANOT_REPETITION(51006, "配方周期不能重复"),
    ADMIN_ENDTIME_CANOT_BIG_STARTTIME(51007, "结束时间不能大于或等于开始时间"),
    API_INPUT_FAILED(51012, "输入错误"),
    API_LIGHT_GROUP_USEING(51014, "灯组正在被别的培养使用"),
    API_LIGHT_GROUP_NOT_EXIST(51015, "灯组不存在"),
    API_PASSWORD_NOT_EQUALS(51017, "新密码和确认密码不一致"),
    API_ACCOUNT_CAN_NOT_DELETE(51018, "用户无权限删除"),
    API_FORMULA_STAGE_CAN_NOT_REPETITION(51019, "阶段编号不能重复"),
    API_STAGE_NUMBER_CAN_NOT_BIG_TEN(51019, "时间段数量不能大于10个"),
    API_LIGHT_NOT_OF_ACCOUNT(51020, "灯具不属于用户"),
    API_IN_PUT_FAILED(51021, "输入数字错误"),
    API_TIMING_OF_ACCOUNT_ID_FILED(51023, "定时所属用户ID错误"),
    API_TIMING_USE_ING(51024, "定时正在使用，不能删除");


    private long code;
    private String message;


    Code(long code, String message) {
        this.code = code;
        this.message = message;
    }


    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
