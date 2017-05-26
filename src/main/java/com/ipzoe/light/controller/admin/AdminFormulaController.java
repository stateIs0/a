package com.ipzoe.light.controller.admin;

import com.ipzoe.light.bean.entity.Formula;
import com.ipzoe.light.bean.request.FormulaRequest;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.repository.FormulaRepository;
import com.ipzoe.light.service.FormulaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create by cxs on 2017/3/28.
 */
@Api("后台配方管理接口")
@RestController
@RequestMapping("/admin/formula")
public class AdminFormulaController {

    @Autowired
    private FormulaService formulaService;



    @PostMapping("/add")
    @ApiOperation("后台增加配方")
    public Response add(@ApiParam("官方配方对象") @RequestBody FormulaRequest formula) {
        return formulaService.adminAddFormula(formula);
    }

    @PostMapping("/delete")
    @ApiOperation("后台删除配方")
    public Response delete(@ApiParam("官方配方对象id") @RequestParam Long formulaId) {
        return formulaService.adminDeleteFormula(formulaId);
    }

    @PostMapping("/update")
    @ApiOperation("后台修改配方")
    public Response update(@ApiParam("官方配方对象") @RequestBody FormulaRequest formula) {
        return formulaService.adminUpdateFormula(formula);
    }

    @PostMapping("/select")
    @ApiOperation("后台查询配方列表")
    public Response select(
            @ApiParam("页码") @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam("纪录数") @RequestParam(required = false, defaultValue = "10") Integer size,
            @ApiParam("关键字") @RequestParam(required = false) String key,
            @ApiParam("用户ID, 非必传，默认查询官方") @RequestParam(required = false, defaultValue = "0") Long accountId) {
        return formulaService.adminSelectFormula(page, size, key, accountId);
    }

}
