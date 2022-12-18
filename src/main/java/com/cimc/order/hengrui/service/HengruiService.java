package com.cimc.order.hengrui.service;

import com.cimc.order.common.domain.SysDataMapping;
import com.cimc.order.hengrui.domain.CimcNode;
import com.cimc.order.hengrui.domain.HengruiOrder;
import com.cimc.order.hengrui.domain.HengruiOrderCancel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Map;

public interface HengruiService {
    /**
     * @description: 运单创建
     **/
    public Map<String,Object> createCimcOrder(HengruiOrder order, SysDataMapping sysDataMapping) throws ParseException;
    /**
     * @description 取消订单
     **/
    public  Map<String,Object>  cancelOrder(HengruiOrderCancel orderCancel);
    /**
     * @description: 运输节点回传
     **/
    public Map<String,Object> returnNode(CimcNode cimcNode) throws IOException, ParseException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    /**
     * @description: 出库单下发至wms
     **/
    public Map<String,Object> putSalesOrder(HengruiOrder order);
}
