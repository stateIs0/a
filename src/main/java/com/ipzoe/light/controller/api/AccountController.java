package com.ipzoe.light.controller.api;

import com.ipzoe.light.bean.entity.Account;
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

/**
 * Created by cxs on 2017/3/23.
 */
@Api(description = "账户",value = "accountController")
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private AuthService authService;

    @Autowired
    private AccountService accountService;

    @ApiOperation("完善账号资料")
    @PostMapping("/fill")
    public Response fill(HttpServletRequest request,
                         @ApiParam("头像地址") @RequestParam String avatar,
                         @ApiParam("昵称") @RequestParam String nickname,
                         @ApiParam("企业名称 非必传") @RequestParam(required = false) String enterprise,
                         @ApiParam("地区 非必传") @RequestParam(required = false) String address,
                         @ApiParam("邮箱") @RequestParam String email) {

        Account account = (Account) request.getAttribute("account");
        return accountService.insert(account, avatar, nickname, enterprise, address, email);
    }


}
