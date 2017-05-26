package com.ipzoe.light.controller.pub;

import com.ipzoe.light.bean.response.LoginResponse;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.service.AdminLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wzx on 2017/3/29.
 */
@Api("后台登录接口")
@RestController
@RequestMapping("/pub/auth")
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @ApiOperation("后台登录")
    @PostMapping("/admin-login")
    public Response<LoginResponse> login(HttpServletRequest request,
                                         @ApiParam("账号名") @RequestParam String username,
                                         @ApiParam("密码") @RequestParam String password ) {

        Response<LoginResponse> response = adminLoginService.adminLogin(username, password);

        return response;
    }
}
