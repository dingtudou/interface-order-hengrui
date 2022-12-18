package com.cimc.order.hengrui.common;

import com.cimc.order.common.cache.SystemCache;
import com.cimc.order.common.domain.SysInterface;
import com.cimc.order.common.service.SysLogService;
import com.cimc.order.common.utils.HttpUtils;
import com.cimc.order.common.utils.JSONUtils;
import com.cimc.order.hengrui.domain.CimcNode;
import com.cimc.order.hengrui.domain.HengruiOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Map;

/**
 * @description
 * @author: dingyongxin
 * @email 1358547120@qq.com
 * @create: 2021-11-11 10:51
 **/
@Slf4j
@Component
public class CustomerService {
    @Autowired
    CustomerServiceUtil thirdServiceUtil;
    @Autowired
    private SysLogService sysLogService;

    /**
     * 路由回传接口
     */
    public Map<String,Object> returnNode(CimcNode cimcNode, HengruiOrder order, String xino) throws ParseException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String urlKey="returnNode";

        SysInterface sysInterface = SystemCache.sysInterfaceMap.get(urlKey);
        String  url= sysInterface.getUrl();

        Map<String, Object> params = thirdServiceUtil.getReturnNodeParam(cimcNode,order,xino);
        Map<String, Object> retmap = HttpUtils.sendPostHttp(url,params);
        log.info("request Url:" + sysInterface.getUrl() + ",\r\nPostData:" + JSONUtils.beanToJson(params) + ",\r\nreturn:"+ JSONUtils.beanToJson(retmap));
        sysLogService.save(urlKey, JSONUtils.beanToJson(params),JSONUtils.beanToJson(retmap));
        return retmap;
    }

    /**
     * 回单照片同步接口
     */
    public Map<String,Object> returnReceipt(CimcNode cimcNode, String xino) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String urlKey="returnReceipt";

        SysInterface sysInterface = SystemCache.sysInterfaceMap.get(urlKey);
        String  url= sysInterface.getUrl();

        Map<String, Object> params = thirdServiceUtil.getRreturnReceiptParam(cimcNode,xino);
        Map<String, Object> retmap = HttpUtils.sendPostHttp(url,params);

        log.info("request Url:" + sysInterface.getUrl() + ",\r\nPostData:" + JSONUtils.beanToJson(params) + ",\r\nreturn:"+ JSONUtils.beanToJson(retmap));
        sysLogService.save(urlKey,JSONUtils.beanToJson(params),JSONUtils.beanToJson(retmap));
        return retmap;
    }



}
