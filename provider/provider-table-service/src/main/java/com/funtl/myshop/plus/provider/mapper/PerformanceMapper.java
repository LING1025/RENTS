package com.funtl.myshop.plus.provider.mapper;

import com.funtl.myshop.plus.provider.domain.Performance;import com.funtl.myshop.plus.provider.domain.ReportForms;
import com.funtl.myshop.plus.provider.dto.LineChartQueryParam;
import com.funtl.myshop.plus.provider.dto.RptQueryParam;
import com.funtl.myshop.plus.provider.dto.RptQueryParams;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface PerformanceMapper extends MyMapper<Performance> {
}
