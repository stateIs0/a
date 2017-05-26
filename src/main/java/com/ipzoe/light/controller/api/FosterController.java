package com.ipzoe.light.controller.api;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.request.FosterRequest;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.service.FosterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cxs on 2017/4/10.
 */
@Api(description = "API 培养接口",value = "fosterController")
@ResponseBody
@Controller
@RequestMapping("/api/foster")
public class FosterController {

    @Autowired
    private FosterService fosterService;


    @ApiOperation("新增培养接口")
    @PostMapping("/add")
    public Response add(HttpServletRequest request,
                        @ApiParam("培养详情 ") @RequestBody FosterRequest fosterRequest) {
        return fosterService.add((Account) request.getAttribute("account"),
                fosterRequest.getName(),
                fosterRequest.getLightGroupId());
    }

    @ApiOperation("编辑培养接口")
    @PostMapping("/update")
    public Response update(HttpServletRequest request,
                           @ApiParam("培养详情") @RequestBody FosterRequest fosterRequest) {
        return fosterService.updateFoster((Account) request.getAttribute("account"),
                fosterRequest.getName(),
                fosterRequest.getLightGroupId(),
                fosterRequest.getFosterId());
    }

    @ApiOperation("删除培养接口")
    @PostMapping("/deleted")
    public Response deleted(@ApiParam("培养ID") @RequestParam Long id) {
        return fosterService.deletedFoster(id);
    }


    @ApiOperation("查询培养接口")
    @PostMapping("/select")
    public Response select(HttpServletRequest request,
                           @ApiParam("培养ID 非必传") @RequestParam(required = false) Long fosterId) {
        return fosterService.selectFoster((Account) request.getAttribute("account"), fosterId);
    }

    @ApiOperation("开启／关闭培养(含一键开关所有）")
    @PostMapping("/set-on-off")
    public Response setOnOff(HttpServletRequest request,
                             @ApiParam("开启／关闭 0：开启  1：关闭") @RequestParam Integer type,
                             @ApiParam("培养ID，非必传（不传则为一键开关所有）") @RequestParam(required = false) Long fosterId) {

        return fosterService.setAllOnOff((Account) request.getAttribute("account"), type, fosterId);
    }

    @ApiOperation("获取所有的培养状态 培养状态1:未开始 2:培养中 3:完成 4:延续培养")
    @PostMapping("/get-status")
    public Response getStatus(HttpServletRequest request) {
        return fosterService.getStatus((Account) request.getAttribute("account"));
    }

    @ApiOperation("根据培养ID查询灯组，0打开灯组，1关闭灯组")
    @PostMapping("/get-light-group")
    public Response getLightGroup(@ApiParam("type = 0 :查询打开灯组，type = 1：查询关闭灯组") @RequestParam Integer type,
                                  @ApiParam("培养ID")@RequestParam Long id) {
        return fosterService.getLightGroup(type,id);
    }




}
