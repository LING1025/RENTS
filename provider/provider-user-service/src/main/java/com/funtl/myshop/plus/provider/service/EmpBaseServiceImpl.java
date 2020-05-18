package com.funtl.myshop.plus.provider.service;

import com.funtl.myshop.plus.commons.utils.PageInfoUtils;
import com.funtl.myshop.plus.provider.domain.EmpList;
import com.funtl.myshop.plus.provider.dto.EmpListDto;
import com.funtl.myshop.plus.provider.dto.EmpQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import com.funtl.myshop.plus.provider.domain.EmpBase;
import com.funtl.myshop.plus.provider.mapper.EmpBaseMapper;
import com.funtl.myshop.plus.provider.api.EmpBaseService;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

@Service(version = "1.0.0")
public class EmpBaseServiceImpl implements EmpBaseService{

    @Resource
    private EmpBaseMapper empBaseMapper;

    @Override
    public PageInfo<EmpListDto> selectEmpListDto(EmpQueryParam empQueryParam) {
        PageHelper.startPage(empQueryParam.getPageNum(),empQueryParam.getPageSize());
        PageInfo<EmpList> pageInfo = new PageInfo<>(empBaseMapper.selectEmpListDto(empQueryParam));
        PageInfo<EmpListDto> result = PageInfoUtils.pageInfo2PageInfoDTO(pageInfo,EmpListDto.class);
        return result;
    }

    @Override
    public Integer insert(EmpBase empBase) {
        return empBaseMapper.insertSelective(empBase);
    }
}