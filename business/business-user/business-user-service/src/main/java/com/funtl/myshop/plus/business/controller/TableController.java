package com.funtl.myshop.plus.business.controller;

import com.funtl.myshop.plus.commons.dto.ResponseResult;
import com.funtl.myshop.plus.provider.api.*;
import com.funtl.myshop.plus.provider.domain.*;
import com.funtl.myshop.plus.provider.dto.*;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 报表信息管理
 */

@Api(tags = "报表相关操作")
@RestController
@RequestMapping(value = "table")
public class TableController {
    @Reference(version = "1.0.0")
    private PerformanceService performanceService;

    @Reference(version = "1.0.0")
    private Org2EmpService org2EmpService;

    @Reference(version = "1.0.0")
    private OrgService orgService;

    @Reference(version = "1.0.0")
    private ItemCodeService itemCodeService;

    @Reference(version = "1.0.0")
    private AspnetUsersService aspnetUsersService;

    @Reference(version = "1.0.0")
    private VEmpService vEmpService;

    @ApiOperation(value = "获取营业报表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAuto", value = "用户id", required = false, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = false, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = false, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "orgAuto", value = "部门id", required = false, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "orgUpAuto", value = "上级部门id", required = false, dataType = "long", paramType = "path")
    })
    @GetMapping(value = "queryMode")
    public ResponseResult<List<ReportFormTwo>> queryMode(@RequestParam(name = "userAuto",required = false) Long userAuto,
                                                          @RequestParam(name = "startDate",required = false) String startDate,
                                                          @RequestParam(name = "endDate",required = false) String endDate,
                                                          @RequestParam(name = "orgAuto",defaultValue = "0") Long orgAuto,
                                                          @RequestParam(name = "orgUpAuto",defaultValue = "0") Long orgUpAuto){
        String startYear = startDate.split("-")[0];
        String endYear = endDate.split("-")[0];
        String startMon = startDate.split("-")[1];
        String endMon = endDate.split("-")[1];
        if (!startYear.equals(endYear) || !startMon.equals(endMon)) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL,"提示：不允许跨年份或月份查询",null);
        }
        LineChartQueryParam lineChartQueryParam = new LineChartQueryParam(userAuto,startDate,endDate,orgAuto,orgUpAuto);
        List<ReportForms> list = vEmpService.selectMode(lineChartQueryParam);
        NumberFormat nt = NumberFormat.getPercentInstance();
        // 设置百分数精确度2即保留两位小数
        //nt.setMinimumFractionDigits(2);
        List<ReportFormTwo> reportFormsList = Lists.newArrayList();
        for(ReportForms reportForms : list){
            ReportFormTwo rs = new ReportFormTwo();
            BeanUtils.copyProperties(reportForms,rs);
            rs.setPLv(nt.format(reportForms.getPaperLv()));
            rs.setCLv(nt.format(reportForms.getCountLv()));
            rs.setVLv(nt.format(reportForms.getVolumeLv()));
            reportFormsList.add(rs);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"查询成功",reportFormsList);
    }

    @ApiOperation(value = "获取试算营业报表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAuto", value = "用户id", required = false, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = false, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = false, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "orgAuto", value = "部门id", required = false, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "orgUpAuto", value = "上级部门id", required = false, dataType = "long", paramType = "path")
    })
    @GetMapping(value = "query")
    public ResponseResult<YearMonthList> query(@RequestParam(name = "userAuto",required = false) Long userAuto,
                                                         @RequestParam(name = "startDate",required = false) String startDate,
                                                         @RequestParam(name = "endDate",required = false) String endDate,
                                                         @RequestParam(name = "orgAuto",defaultValue = "0") Long orgAuto,
                                                         @RequestParam(name = "orgUpAuto",defaultValue = "0") Long orgUpAuto){
        String startYear = startDate.split("-")[0];
        String endYear = endDate.split("-")[0];
        String startMon = startDate.split("-")[1];
        String endMon = endDate.split("-")[1];
        if (!startYear.equals(endYear) || !startMon.equals(endMon)) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL,"提示：不允许跨年份或月份查询",null);
        }
        LineChartQueryParam lineChartQueryParam = new LineChartQueryParam(userAuto,startDate,endDate,orgAuto,orgUpAuto);
        List<MonthListDto> monthListDtos = vEmpService.selectTest(lineChartQueryParam);
        List<PCountMoneys> thisMonth = Lists.newArrayList();
        List<PCountMoneyLast> lastMonth = Lists.newArrayList();
        for(MonthListDto monthListDto : monthListDtos){
            //本月
            PCountMoneys pCountMoneys = new PCountMoneys();
            BeanUtils.copyProperties(monthListDto,pCountMoneys);
            //上个月
            PCountMoneyLast pCountMoneyLast = new PCountMoneyLast();
            BeanUtils.copyProperties(monthListDto,pCountMoneyLast);
            pCountMoneyLast.setYearMonLast(monthListDto.getYearMon());
            pCountMoneyLast.setDaysLast(monthListDto.getDays());
            pCountMoneyLast.setPCountLast(monthListDto.getPCount());
            pCountMoneyLast.setPMoneyLast(monthListDto.getPMoney());

            if(monthListDto.getYearMon() == null || monthListDto.getYearMon().split("-")[1].equals(startMon)){
                thisMonth.add(pCountMoneys);
            }
            if(monthListDto.getYearMon() == null || !monthListDto.getYearMon().split("-")[1].equals(startMon)){
                lastMonth.add(pCountMoneyLast);
            }
        }
        YearMonthList yearMonthList = new YearMonthList();
        yearMonthList.setThisMonth(thisMonth);
        yearMonthList.setLastMonth(lastMonth);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"查询成功",yearMonthList);
    }
}
