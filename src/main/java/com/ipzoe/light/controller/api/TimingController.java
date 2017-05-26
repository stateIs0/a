package com.ipzoe.light.controller.api;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.request.TimingRequest;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.repository.TimingRepository;
import com.ipzoe.light.service.TimingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cxs on 2017/4/14.
 */
@RestController
@RequestMapping("api/light/timing")
@Api(description = "定时接口", value = "timingController")
public class TimingController {

    @Autowired
    private TimingService timingService;

    @Autowired
    private TimingRepository timingRepository;

    @PostMapping("/add")
    @ApiOperation("新增定时")
    public Response add(HttpServletRequest request,
                        @ApiParam("定时实体") @RequestBody TimingRequest timingRequest) {

        return timingService.addTiming((Account) request.getAttribute("account"), timingRequest);
    }

    @PostMapping("/update")
    @ApiOperation("更新定时")
    public Response update(HttpServletRequest request,
                           @ApiParam("定时实体") @RequestBody TimingRequest timingRequest) {
        return timingService.update((Account) request.getAttribute("account"), timingRequest);
    }

    @PostMapping("/delete")
    @ApiOperation("删除定时")
    public Response deleted(HttpServletRequest request,
                            @ApiParam("定时ID") @RequestParam Long id) {
        return timingService.deleted((Account) request.getAttribute("account"), id);
    }

    @PostMapping("select")
    @ApiOperation("查询定时")
    public Response select(HttpServletRequest request) {
        Account account = (Account) request.getAttribute("account");
        return new Response<>(timingRepository.selectByAccountId(account.getId()));
    }

}
