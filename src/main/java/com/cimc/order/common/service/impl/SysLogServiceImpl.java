package com.cimc.order.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cimc.order.common.cache.SystemCache;
import com.cimc.order.common.domain.SysLog;
import com.cimc.order.common.mapper.SysLogMapper;
import com.cimc.order.common.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 13585
* @description 针对表【sys_log(系统日志)】的数据库操作Service实现
* @createDate 2022-09-13 13:56:22
*/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
    implements SysLogService{
    @Autowired
    SysLogMapper sysLogMapper;


    @Override
    public boolean save(String interfaceCode, String operParam, String retResult) {
        SysLog sysLog=new SysLog();
        sysLog.setProjectCode(SystemCache.projectCode);
        sysLog.setInterfaceCode(interfaceCode);
        sysLog.setOperParam(operParam);
        sysLog.setRetResult(retResult);
        return this.save(sysLog);
    }
}




