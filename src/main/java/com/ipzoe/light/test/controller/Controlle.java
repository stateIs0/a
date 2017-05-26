package com.ipzoe.light.test.controller;

import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.test.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cxs on 2017/4/14.
 */
public class Controlle {


    @Autowired
    private TestService service;

    @PostMapping("/select")
    @ApiOperation("查询帖子列表")
    public Response select (@ApiParam("关键字") @RequestParam(required = false) String key,
                           @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") int page,
                            @ApiParam("数量")@RequestParam(required = false,defaultValue = "10") int size) {
        return service.select(key,page,size);
    }



    @PostMapping("/select-content")
    @ApiOperation("根据ID查询帖子内容")
    public Response selectContent (@ApiParam("id") @RequestParam(required = false) Long id ,
                                   @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") int page,
                                   @ApiParam("数量")@RequestParam(required = false,defaultValue = "10") int size) {
        return service.selectContent(id,page,size);
    }



}
