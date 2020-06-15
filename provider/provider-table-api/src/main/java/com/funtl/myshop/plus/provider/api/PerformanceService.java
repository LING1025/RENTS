package com.funtl.myshop.plus.provider.api;

import com.funtl.myshop.plus.provider.domain.Performance;
import com.funtl.myshop.plus.provider.domain.ReportForms;
import com.funtl.myshop.plus.provider.dto.RptQueryParam;

import java.util.Date;
import java.util.List;

public interface PerformanceService{
    /**
     * 根据年份月份获取营业报表信息
     * @param rptQueryParam
     * @return
     */
//    List<ReportForms> selectByYM(RptQueryParam rptQueryParam);
    ReportForms selectByYM(RptQueryParam rptQueryParam);

    List<ReportForms> selectByUpId(Integer year, Integer month, Long upUnit);

    /**
     * 获取下级数据
     * @param year
     * @param month
     * @return
     */
    List<ReportForms> selectModeOne(Integer year, Integer month,String startDate,String endDate);

    /**
     * 获取业代数据
     * @param year
     * @param month
     * @param startDate
     * @param endDate
     * @return
     */
    List<ReportForms> selectModeZero(Integer year, Integer month,String startDate,String endDate);


}
