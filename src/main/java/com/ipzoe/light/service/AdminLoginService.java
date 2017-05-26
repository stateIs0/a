package com.ipzoe.light.service;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.entity.Admin;
import com.ipzoe.light.bean.response.Code;
import com.ipzoe.light.bean.response.LoginResponse;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.component.TokenComponent;
import com.ipzoe.light.repository.AccountRepository;
import com.ipzoe.light.repository.AdminLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzx on 2017/3/29.
 */
@Transactional
@Service
public class AdminLoginService {

    @Autowired
    private AdminLoginRepository adminLoginRepository;

    @Autowired
    private TokenComponent tokenComponent;

    public Response<LoginResponse> adminLogin(String username, String password) {

        //根据账号名查询账号
        Admin admin = adminLoginRepository.findByUsername(username);

        //校验账号是否存在
        if (admin == null) return new Response<>(Code.ADMIN_NOT_EXIST);

        //校验账号密码
        if (!admin.getPassword().equals(password)) return new Response<>(Code.ADMIN_PASSWORD_ERROR);

        //组装登陆返回值
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(username);
        loginResponse.setAvatar(admin.getAvatar());
        loginResponse.setNickname(admin.getNickname());
        loginResponse.setEmail(admin.getEmail());
        loginResponse.setEnterprise(admin.getEnterprise());
        loginResponse.setAddress(admin.getAddress());
        loginResponse.setAccountId(admin.getId());

        String token = tokenComponent.generateToken(admin.getId().toString(), admin.getUsername());
        loginResponse.setToken("Bearer " + token);

        return new Response<>(loginResponse);

    }


}
