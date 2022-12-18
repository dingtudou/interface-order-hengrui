package com.cimc.order.hengrui.common;

import com.cimc.order.common.cache.SystemCache;
import com.cimc.order.common.domain.SysDataMapping;
import com.cimc.order.common.domain.SysInterface;
import com.cimc.order.common.service.SysLogService;
import com.cimc.order.common.utils.HttpUtils;
import com.cimc.order.common.utils.JSONUtils;
import com.cimc.order.hengrui.domain.HengruiOrder;
import com.cimc.order.hengrui.domain.HengruiOrderCancel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author: dingyongxin
 * @create: 2021-07-21 09:24
 **/

@Slf4j
@Component
public class CimcTmsService {
    @Autowired
    private CimcTmsUtil cimcTmsUtils;
    @Autowired
    private SysLogService sysLogService;

    public  Map<String,Object>  createCimcOrder(HengruiOrder order, SysDataMapping sysDataMapping) throws ParseException {
        String urlKey="changeOrder";
        SysInterface sysInterface = SystemCache.sysInterfaceMap.get(urlKey);
        String url=sysInterface.getUrl();

        Map<String, Object> params = cimcTmsUtils.getChangeOrderParams(order,sysDataMapping);
        Map<String, Object> retmap = HttpUtils.sendPostHttp(url, params);

        log.info("request URL:" + url + ",\r\nPostData:" + JSONUtils.beanToJson(params) + ",\r\nreturn:"+ JSONUtils.beanToJson(retmap));
        sysLogService.save(urlKey,JSONUtils.beanToJson(params),JSONUtils.beanToJson(retmap));
        return retmap;
    }
    /**
     * @description 取消订单
     **/
    public  Map<String,Object>  cancelOrder(HengruiOrderCancel orderCancel){
        String urlKey="cancelOrder";
        SysInterface sysInterface = SystemCache.sysInterfaceMap.get(urlKey);
        String url=sysInterface.getUrl();

        Map<String, Object> params = cimcTmsUtils.getcancelOrderParams(orderCancel);
        Map<String, Object> retmap = HttpUtils.sendPostHttp(url, params);

        log.info("request URL:" + url + ",\r\nPostData:" + JSONUtils.beanToJson(params) + ",\r\nreturn:"+ JSONUtils.beanToJson(retmap));
        sysLogService.save(urlKey,JSONUtils.beanToJson(params),JSONUtils.beanToJson(retmap));
        return retmap;
    }
}
