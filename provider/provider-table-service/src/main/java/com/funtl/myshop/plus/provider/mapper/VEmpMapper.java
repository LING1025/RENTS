package com.funtl.myshop.plus.provider.mapper;

import com.funtl.myshop.plus.provider.domain.ReportForms;
import com.funtl.myshop.plus.provider.domain.VEmp;
import com.funtl.myshop.plus.provider.dto.LastMonthListDto;
import com.funtl.myshop.plus.provider.dto.LineChartQueryParam;
import com.funtl.myshop.plus.provider.dto.MonthListDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface VEmpMapper extends MyMapper<VEmp> {
    List<ReportForms> selectMode(@Param("param") LineChartQueryParam lineChartQueryParam);

    List<MonthListDto> selectTest(@Param("param") LineChartQueryParam lineChartQueryParam);

}
