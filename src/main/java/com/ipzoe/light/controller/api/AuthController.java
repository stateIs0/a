package com.ipzoe.light.controller.api;

import com.ipzoe.light.bean.response.LoginResponse;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.service.AccountService;
import com.ipzoe.light.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static javafx.scene.input.KeyCode.R;


/**
 * Created by wzx on 2017/3/27.
 */
@Api(description = "API登录接口",value = "authController")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private AuthService authService;

    @Autowired
    private AccountService accountService;

    @ApiOperation("客户端登录")
    @PostMapping("/login")
    public Response<LoginResponse> login(@ApiParam("账号名") @RequestParam String username,
                                         @ApiParam("密码") @RequestParam String password ) {

        log.info(username + " " + password);
        return authService.accountLogin(username, password);
    }

    @ApiOperation("注册账号")
    @PostMapping("/register")
    public Response add(@ApiParam("手机号") @RequestParam String username,
                        @ApiParam("密码") @RequestParam String password,
                        @ApiParam("验证码") @RequestParam String code) {

        return accountService.register(username,password,code);
    }

    @ApiOperation("发送短信验证码")
    @PostMapping("/sms-recode")
    public Response sendSmsCode(@ApiParam("手机号") @RequestParam String phone) {

        return accountService.sendSmsCode(phone);
    }

    @ApiOperation("修改密码")
    @PostMapping("/modify-password")
    public Response findPassword(@ApiParam("手机号") @RequestParam String phone,
                                 @ApiParam("验证码") @RequestParam String code,
                                 @ApiParam("新密码") @RequestParam String password,
                                 @ApiParam("确认密码") @RequestParam String affirm) {
        return accountService.modifyPassword(phone, code, password, affirm);
    }

}
