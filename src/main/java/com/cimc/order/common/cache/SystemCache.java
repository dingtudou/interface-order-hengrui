package com.cimc.order.common.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cimc.order.common.domain.*;
import com.cimc.order.common.service.*;
import com.cimc.order.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SystemCache implements ApplicationRunner {

    @Autowired
    private SysParamService sysParamService;
    @Autowired
    private SysInterfaceService sysInterfaceService;
    @Autowired
    private SysTaskService sysTaskService;
    @Autowired
    private SysDictDataService sysDictDataService;
    @Autowired
    private SysDataMappingService sysDataMappingService;


    public static String projectCode;

    @Value("${cimc.projectCode}")
    public void setProjectCode(String str){
        projectCode=str;
    }

    //接口信息
    public static Map<String,SysInterface> sysInterfaceMap =new HashMap<>();
    //配置信息
    public static Map<String,String> sysParamMap =new HashMap<>();
    //定时任务信息
    public static List<SysTask> sysTaskList=new ArrayList<>();
    //字典表信息
    public static List<SysDictData> sysDictDataList=new ArrayList<>();
    //数据映射表信息
    public static List<SysDataMapping> sysDataMappingList=new ArrayList<>();
    @Override
    public void run(ApplicationArguments args){
        log.info("============缓存系统基础信息-------开始==============");

        //加载接口信息
        QueryWrapper<SysInterface>  sysInterfaceQueryWrapper=new QueryWrapper<>();
        sysInterfaceQueryWrapper.eq("project_code",projectCode);
        sysInterfaceQueryWrapper.eq("status",1);
        List<SysInterface> sysInterfaceList=sysInterfaceService.list(sysInterfaceQueryWrapper);
        //加载配置信息
        QueryWrapper<SysParam>  sysParamQueryWrapper=new QueryWrapper<>();
        sysParamQueryWrapper.eq("project_code",projectCode);
        sysParamQueryWrapper.eq("status",1);
        List<SysParam> sysParamList =sysParamService.list(sysParamQueryWrapper);
        //加载配置信息
        QueryWrapper<SysTask>  sysTaskQueryWrapper=new QueryWrapper<>();
        sysTaskQueryWrapper.eq("project_code",projectCode);
        sysTaskQueryWrapper.eq("status",1);
        sysTaskList =sysTaskService.list(sysTaskQueryWrapper);
        //加载字典信息
        QueryWrapper<SysDictData>  sysDictDataQueryWrapper=new QueryWrapper<>();
        sysDictDataQueryWrapper.eq("project_code",projectCode);
        sysDictDataQueryWrapper.eq("status",1);
        sysDictDataList =sysDictDataService.list(sysDictDataQueryWrapper);
        //加载数据映射信息
        QueryWrapper<SysDataMapping>  sysDataMappingQueryWrapper=new QueryWrapper<>();
        sysDataMappingQueryWrapper.eq("project_code",projectCode);
        //sysDataMappingQueryWrapper.eq("status",1);

        sysDataMappingList =sysDataMappingService.list(sysDataMappingQueryWrapper);

        if(null!=sysInterfaceList){
            for (SysInterface sysInterface: sysInterfaceList) {
                if (null!=sysInterface){
                    sysInterfaceMap.put(sysInterface.getCode(),sysInterface);
                }
            }
        }

        if(null!=sysParamList){
            for (SysParam sysParam: sysParamList) {
                if (null!=sysParam){
                    sysParamMap.put(sysParam.getCode(), sysParam.getValue());
                }
            }
        }
        log.info("============缓存系统基础信息-------结束==============");
    }

    public static SysDataMapping getSysDataMappingByCustomerCode(String customerCode,String mappingType) {
        SysDataMapping sysDataMapping=null;
        if (StringUtils.isNotBlank(customerCode)&&StringUtils.isNotBlank(mappingType)&&null!=sysDataMappingList&&sysDataMappingList.size()>0) {
            for (SysDataMapping sdm : sysDataMappingList) {
                if (mappingType.equals(sdm.getMappingType())&&customerCode.equals(sdm.getCustomerCode())){
                    sysDataMapping=sdm;
                }
            }
        }
        return sysDataMapping;
    }
    public static SysDataMapping getSysDataMappingByCustomerValue(String customerValue,String mappingType) {
        SysDataMapping sysDataMapping=null;
        if (StringUtils.isNotBlank(customerValue)&&StringUtils.isNotBlank(mappingType)&&null!=sysDataMappingList&&sysDataMappingList.size()>0) {
            for (SysDataMapping sdm : sysDataMappingList) {
                if (mappingType.equals(sdm.getMappingType())&&customerValue.equals(sdm.getCustomerValue())){
                    sysDataMapping=sdm;
                }
            }
        }
        return sysDataMapping;
    }
    public static SysDataMapping getSysDataMappingByCimcCode(String cimcCode,String mappingType) {
        SysDataMapping sysDataMapping=null;
        if (StringUtils.isNotBlank(cimcCode)&&StringUtils.isNotBlank(mappingType)&&null!=sysDataMappingList&&sysDataMappingList.size()>0) {
            for (SysDataMapping sdm : sysDataMappingList) {
                if (mappingType.equals(sdm.getMappingType())&&cimcCode.equals(sdm.getCimcCode())){
                    sysDataMapping=sdm;
                }
            }
        }
        return sysDataMapping;
    }
}
