package com.cimc.order.hengrui.common;

import com.cimc.order.common.cache.SystemCache;
import com.cimc.order.common.domain.SysInterface;
import com.cimc.order.common.service.SysLogService;
import com.cimc.order.common.utils.HttpUtils;
import com.cimc.order.common.utils.JSONUtils;
import com.cimc.order.hengrui.domain.HengruiOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Map;

/**
 * @description
 * @author: dingyongxin
 * @create: 2021-07-21 09:24
 **/

@Slf4j
@Component
public class CimcWmsService {
    @Autowired
    private CimcWmsUtil cimcWmsUtil;
    @Autowired
    private SysLogService sysLogService;

    /**
     * @description: 出库单下发至wms
     **/
    public  Map<String,Object>  putSalesOrder(HengruiOrder order){
        String urlKey="putSalesOrder";
        SysInterface sysInterface = SystemCache.sysInterfaceMap.get(urlKey);
        String url=sysInterface.getUrl();

        Map<String, Object> urlParams = cimcWmsUtil.getWmsUrlParams(sysInterface.getMethodName(),sysInterface.getParamName());
        Map<String, Object> bodyParams = cimcWmsUtil.getPutSalesOrderParams(order);
        Map<String, Object> retmap = HttpUtils.sendPostHttp(url, urlParams,bodyParams);

        log.info("request URL:" + url + ",\r\nurlParams:" + JSONUtils.beanToJson(urlParams) +",\r\nbodyParams:" + JSONUtils.beanToJson(bodyParams) + ",\r\nreturn:"+ JSONUtils.beanToJson(retmap));
        sysLogService.save(urlKey,JSONUtils.beanToJson(bodyParams),JSONUtils.beanToJson(retmap));
        return retmap;
    }
}
