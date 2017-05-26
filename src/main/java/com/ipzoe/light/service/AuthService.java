package com.ipzoe.light.service;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.response.Code;
import com.ipzoe.light.bean.response.LoginResponse;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.component.TokenComponent;
import com.ipzoe.light.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wzx on 2017/3/27.
 */
@Transactional
@Service
public class AuthService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenComponent tokenComponent;

    public Response<LoginResponse> accountLogin(String username, String password) {

        //根据账号名查询账号
        Account account = accountRepository.findByUsername(username);

        //校验账号是否存在
        if (account == null) return new Response<>(Code.API_ACCOUNT_NOT_EXIST);

        //校验账号密码
        if (!account.getPassword().equals(password)) return new Response<>(Code.API_ACCOUNT_LOGIN_PASSWORD_ERROR);

        //组装登陆返回值
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(username);
        loginResponse.setAvatar(account.getAvatar());
        loginResponse.setNickname(account.getNickname());
        loginResponse.setEmail(account.getEmail());
        loginResponse.setEnterprise(account.getEnterprise());
        loginResponse.setAddress(account.getAddress());
        loginResponse.setAccountId(account.getId());

        String token = tokenComponent.generateToken(account.getId().toString(), account.getUsername());
        loginResponse.setToken("Bearer " + token);

        return new Response<>(loginResponse);

    }




}
