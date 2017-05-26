package com.ipzoe.light.controller.api;


import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.request.LightGroupRequest;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.service.LightGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wzx on 2017/3/24.
 */
@Api(description = "灯组接口",value = "lightGroupController")
@RestController("lightGroupController")
@RequestMapping("/api/light-group")
public class LightGroupController {

    @Autowired
    private LightGroupService lightGroupService;

    @ApiOperation("新增灯组")
    @PostMapping("/create")
    public Response add(HttpServletRequest request,
                        @ApiParam("灯组关联数据") @RequestBody LightGroupRequest lightGroupRequest) {
        Account account = (Account) request.getAttribute("account");
        return lightGroupService.insert(lightGroupRequest, account);
    }

    @ApiOperation("修改灯组")
    @PostMapping("/update")
    public Response update(HttpServletRequest request,
                           @ApiParam("灯组关联数据") @RequestBody LightGroupRequest lightGroupRequest) {
        Account account = (Account) request.getAttribute("account");
        return lightGroupService.update(lightGroupRequest, account);
    }

    @ApiOperation("删除灯组")
    @PostMapping("/delete")
    public Response delete(@ApiParam("灯组ID") @RequestParam Long lightGroupId) {
        return lightGroupService.delete(lightGroupId);
    }

    @ApiOperation("查询空闲灯组")
    @PostMapping("/query")
    public Response query(HttpServletRequest request,
                          @ApiParam("查询空闲灯组type=1") @RequestParam Integer type) {
        Account account = (Account) request.getAttribute("account");
        return lightGroupService.query(account);
    }

    @ApiOperation("获取打开灯组／获取关闭灯组")
    @PostMapping("/select-on-off")
    public Response select(HttpServletRequest request,
                          @ApiParam("type=0:打开，type=1:关闭") @RequestParam Integer type) {
        Account account = (Account) request.getAttribute("account");
        return lightGroupService.selectLightGroup(account, type);
    }



}
