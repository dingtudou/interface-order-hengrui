package com.cimc.order.hengrui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cimc.order.common.domain.SysDataMapping;
import com.cimc.order.common.utils.StringUtils;
import com.cimc.order.hengrui.common.CimcTmsService;
import com.cimc.order.hengrui.common.CimcWmsService;
import com.cimc.order.hengrui.common.CustomerService;
import com.cimc.order.hengrui.domain.CimcNode;
import com.cimc.order.hengrui.domain.HengruiOrder;
import com.cimc.order.hengrui.domain.HengruiOrderCancel;
import com.cimc.order.hengrui.service.HengruiOrderService;
import com.cimc.order.hengrui.service.HengruiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HengruiServiceImpl implements HengruiService {
    @Autowired
    CimcTmsService cimcTmsService;
    @Autowired
    CimcWmsService cimcWmsService;
    @Autowired
    CustomerService customerService;
    @Autowired
    HengruiOrderService hengruiOrderService;

    /**
     * @description: 运单创建
     **/
    @Override
    public Map<String,Object> createCimcOrder(HengruiOrder order, SysDataMapping sysDataMapping) throws ParseException {
        return cimcTmsService.createCimcOrder(order,sysDataMapping);
    }
    /**
     * @description 取消订单
     **/
    public  Map<String,Object>  cancelOrder(HengruiOrderCancel orderCancel){
        return cimcTmsService.cancelOrder(orderCancel);
    }
    /**
     * @description: 出库单下发至wms
     **/
    @Override
    public Map<String,Object> putSalesOrder(HengruiOrder order){
        return cimcWmsService.putSalesOrder(order);
    }

    /**
     * @description: 运输节点回传
     **/
    @Override
    public Map<String,Object> returnNode(CimcNode cimcNode) throws IOException, ParseException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        boolean isSuccess=true;
        List<String> msgs=new ArrayList<>();
        Map<String,Object> retMap=null;

        //-------------处理节点状态
        String billcode= cimcNode.getBillCode();
        String[] xmnos =cimcNode.getXMNO().split(",");
        //3物流信息接口（物流公司->CDMS）
        for (String xmno:xmnos) {
            String code = "500";
            String msg = "接口数据库中未查询到对应单子";

            QueryWrapper<HengruiOrder> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("client_order",xmno);
            queryWrapper.last("limit 1");
            HengruiOrder order=hengruiOrderService.getOne(queryWrapper);

            if (order!=null) {
                if ("REC".equals(billcode)) {
                    retMap = customerService.returnReceipt(cimcNode, xmno);
                } else {
                    retMap = customerService.returnNode(cimcNode, order, xmno);
                }
                if (null != retMap) {
                    code =(String) retMap.get("code");
                    msg = (String) retMap.get("msg");
                }
            }

            isSuccess = isSuccess && "0".equals(code);
            msgs.add(xmno + ":" + msg);
        }
        retMap =new HashMap<>();
        retMap.put("code",isSuccess?200:500);
        retMap.put("msg", StringUtils.join(msgs));

        return retMap;
    }

}
