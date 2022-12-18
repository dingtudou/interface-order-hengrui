package com.cimc.order.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cimc.order.common.domain.SysLog;

/**
* @author 13585
* @description 针对表【sys_log(系统日志)】的数据库操作Service
* @createDate 2022-09-13 13:56:22
*/
public interface SysLogService extends IService<SysLog> {
    boolean save(String interfaceKey, String operParam, String retResult);
}
