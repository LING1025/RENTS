package com.funtl.myshop.plus.provider.api;

import com.funtl.myshop.plus.provider.domain.ReportForms;
import com.funtl.myshop.plus.provider.dto.LastMonthListDto;
import com.funtl.myshop.plus.provider.dto.LineChartQueryParam;
import com.funtl.myshop.plus.provider.dto.MonthListDto;

import java.util.List;

public interface VEmpService{
    /**
     * 获取部门/课数据
     * @param lineChartQueryParam
     * @return
     */
    List<ReportForms> selectMode(LineChartQueryParam lineChartQueryParam);

    List<MonthListDto> selectTest(LineChartQueryParam lineChartQueryParam);



}
