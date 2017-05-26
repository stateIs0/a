package com.ipzoe.light.controller.api;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.entity.Formula;
import com.ipzoe.light.bean.request.FormulaRequest;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.repository.FormulaRepository;
import com.ipzoe.light.service.FormulaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cxs on 2017/3/24.
 */
@Api(description = "Api 配方接口",value = "formulaController")
@RestController
@RequestMapping("/api/formula")
public class FormulaController {

    @Autowired
    private FormulaService formulaService;

    @Autowired
    private FormulaRepository formulaRepository;

    @PostMapping("/add")
    @ApiModelProperty("新增配方接口")
    public Response add(HttpServletRequest request,
                        @ApiParam("配方实体") @RequestBody FormulaRequest formulaRequest) {
        return formulaService.addFormula((Account) request.getAttribute("account"), formulaRequest);
    }

    @PostMapping("/delete")
    @ApiModelProperty("删除配方接口")
    public Response delete(HttpServletRequest request,
                           @ApiParam("配方ID") @RequestParam Long id) {
        return formulaService.deleteFormula((Account) request.getAttribute("account"), id);
    }


    @PostMapping("/update")
    @ApiModelProperty("修改配方接口")
    public Response update(HttpServletRequest request,
                           @ApiParam("配方实体") @RequestBody FormulaRequest formulaRequest) {
        return formulaService.updateFormula((Account) request.getAttribute("account"), formulaRequest);
    }


    @PostMapping("/select")
    @ApiModelProperty("查询配方接口")
    public Response select(HttpServletRequest request,
                           @ApiParam("关键字 非必传") @RequestParam(required = false) String key,
                           @ApiParam("查询类型1=平台，2=自己") @RequestParam Integer type) {
        return formulaService.selectFormula((Account) request.getAttribute("account"), key, type);
    }


    @ApiOperation("根据ID查询配方")
    @PostMapping("/select-id")
    public Response selectById(  @ApiParam("id") @RequestParam Long id) {
        return new Response<>(formulaService.selectByPrimaryKey(id));
    }

}
