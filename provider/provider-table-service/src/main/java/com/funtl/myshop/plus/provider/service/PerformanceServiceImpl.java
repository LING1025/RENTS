package com.funtl.myshop.plus.provider.service;

import javax.annotation.Resource;
import com.funtl.myshop.plus.provider.domain.Performance;
import com.funtl.myshop.plus.provider.domain.ReportForms;
import com.funtl.myshop.plus.provider.dto.RptQueryParam;
import com.funtl.myshop.plus.provider.mapper.PerformanceMapper;
import com.funtl.myshop.plus.provider.api.PerformanceService;
import org.apache.dubbo.config.annotation.Service;

import java.util.Date;
import java.util.List;

@Service(version = "1.0.0")
public class PerformanceServiceImpl implements PerformanceService{

    @Resource
    private PerformanceMapper performanceMapper;

    @Override
    public ReportForms selectByYM(RptQueryParam rptQueryParam) {
        return performanceMapper.selectByYM(rptQueryParam);
    }

    @Override
    public List<ReportForms> selectByUpId(Integer year, Integer month, Long upUnit) {
        return performanceMapper.selectByUpId(year,month,upUnit);
    }

    /*@Override
    public List<ReportForms> selectByYM(RptQueryParam rptQueryParam) {
        return performanceMapper.selectByYM(rptQueryParam);
    }*/
}
