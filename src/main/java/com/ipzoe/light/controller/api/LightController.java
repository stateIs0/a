package com.ipzoe.light.controller.api;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.request.LightRequest;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.service.LightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wzx on 2017/3/24.
 */
@Api(description = "灯具",value = "lightController")
@RestController("lightController")
@RequestMapping("/api/light")
public class LightController {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private LightService lightService;

    @ApiOperation("新增灯具数组")
    @PostMapping("/create")
    public Response add(HttpServletRequest request,
                        @ApiParam("灯具数组") @RequestBody List<LightRequest> lightList) {
        Account account = (Account) request.getAttribute("account");
        return lightService.insert(account, lightList);
    }

    @ApiOperation("更新灯具信息")
    @PostMapping("/update")
    public Response update(@ApiParam("灯具ID") @RequestParam Long lightId,
                           @ApiParam("编号 ") @RequestParam String code) {
        return lightService.update(lightId, code);
    }

    @ApiOperation("删除灯具")
    @PostMapping("/delete")
    public Response delete(@ApiParam("灯具ID") @RequestParam Long lightId) {
        return lightService.delete(lightId);
    }


    @ApiOperation("获取指定灯组ID下的 + 空闲的灯具（空闲为true，否则为false）")
    @PostMapping("/query")
    public Response queryLight(HttpServletRequest request,
                               @ApiParam("灯组ID ") @RequestParam Long groupId) {
        Account account = (Account) request.getAttribute("account");
        return lightService.queryLight(account, groupId);
    }

    @ApiOperation("获取指定灯组ID下的灯具")
    @PostMapping("/select")
    public Response select(@ApiParam("灯组ID") @RequestParam Long groupId) {
        return lightService.selectLight(groupId);
    }


    @ApiOperation("获取所有／获取空闲")
    @PostMapping("/select-all")
    public Response selectAll(HttpServletRequest request,
                              @ApiParam("type 0:所有，1:空闲") @RequestParam Long type) {
        return lightService.selectAllLight((Account) request.getAttribute("account"), type);
    }


    @ApiOperation("编辑灯具时检测")
    @GetMapping("/detection")
    public Response detection(HttpServletRequest request,
                              @ApiParam("灯具ID") @RequestParam Long lightId) {
        return lightService.detectionLight((Account) request.getAttribute("account"), lightId);
    }

}
