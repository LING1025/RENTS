package com.funtl.myshop.plus.provider.service;

import javax.annotation.Resource;
import com.funtl.myshop.plus.provider.domain.Performance;
import com.funtl.myshop.plus.provider.domain.ReportForms;
import com.funtl.myshop.plus.provider.dto.LineChartQueryParam;
import com.funtl.myshop.plus.provider.dto.RptQueryParam;
import com.funtl.myshop.plus.provider.dto.RptQueryParams;
import com.funtl.myshop.plus.provider.mapper.PerformanceMapper;
import com.funtl.myshop.plus.provider.api.PerformanceService;
import org.apache.dubbo.config.annotation.Service;

import java.util.Date;
import java.util.List;

@Service(version = "1.0.0")
public class PerformanceServiceImpl implements PerformanceService{

    @Resource
    private PerformanceMapper performanceMapper;

}
