package com.ipzoe.light.controller.admin;

import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.service.AdminService;
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
 * Created by wzx on 2017/3/28.
 */
@Api("后台账户管理")
@RestController
@RequestMapping("/admin/account")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("查询所有用户")
    @PostMapping("/query")
    public Response query(
                          @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer page,
                          @ApiParam("页数") @RequestParam(required = false,defaultValue = "10") Integer size,
                          @ApiParam("关键字") @RequestParam(required = false,defaultValue = "") String key){

        return adminService.query(page,size,key);

    }




}
