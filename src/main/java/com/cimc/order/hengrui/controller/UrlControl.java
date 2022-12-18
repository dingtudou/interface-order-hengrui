package com.cimc.order.hengrui.controller;


import com.alibaba.fastjson.JSONObject;
import com.cimc.order.common.cache.SystemCache;
import com.cimc.order.common.domain.AjaxResult;
import com.cimc.order.common.domain.SysDataMapping;
import com.cimc.order.common.utils.JSONUtils;
import com.cimc.order.hengrui.domain.*;
import com.cimc.order.hengrui.service.*;
import com.cimc.weblog.util.MailService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api/v2")
@Slf4j
public class UrlControl {
    @Autowired
    HengruiService hengruiService;
    //订单服务
    @Autowired
    private HengruiOrderService hengruiOrderService;
    @Autowired
    private HengruiOrderCancelService hengruiOrderCancelService;
    @Autowired
    private HengruiDetailService hengruiDetailService;
    @Autowired
    private HengruiTestInfoService hengruiTestInfoService;

    @Autowired
    private MailService mailService;
    @Value("${email.toBusiness}")
    private String toBusiness;

    /**
     * 运单创建接口
     */
    @RequestMapping(value="/createOrder",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AjaxResult createOrder(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        //参数约定从body传输，post
        JSONObject jsonParam = JSONUtils.getJSONParam(request);
        HengruiOrder order = JSONObject.parseObject(String.valueOf(jsonParam), HengruiOrder.class);
        //验证必填
        Assert.notNull(order,"无法获取参数");

        List<HengruiDetail> Details=order.getDetailList();
        Assert.isTrue(Details!=null&&Details.size()>0,"无法获取订单明细");

        //验证是否多温区
        Set<String> temperatureRanges = new TreeSet<>();
        for (HengruiDetail detail:Details) {
            temperatureRanges.add(detail.getTemperatureRange());
        }
        Assert.isTrue(temperatureRanges.size()==1,"无法接收多温区订单温区");
        //验证温区是否映射
        SysDataMapping sysDataMapping= SystemCache.getSysDataMappingByCustomerValue(temperatureRanges.iterator().next(),"temperatureRange");
        Assert.notNull(sysDataMapping,"未查询到映射温区");

        //调用中集
        Map<String, Object> cimcMap = hengruiService.createCimcOrder(order,sysDataMapping);
        String code = cimcMap.get("code").toString();
        String msg = cimcMap.get("msg").toString();
        String orderid = cimcMap.get("OrderId")==null?null:cimcMap.get("OrderId").toString();
        order.setCimcCode(code);
        order.setCimcMsg(msg);
        order.setCimcOrderid(orderid);
        /*Map<String, Object> cimcMap=new HashMap<>();
        String code = "200";
        order.setCimcOrderid("123");*/

        //调用wms出库单下单
        if ("200--".equals(code)) {
            try {
                Map<String, Object> wmsMap = hengruiService.putSalesOrder(order);
                Map<String, Object> responseMap = (Map<String, Object>) wmsMap.get("Response");
                Map<String, Object> returnMap = (Map<String, Object>) responseMap.get("return");

                String returnCode = null==returnMap.get("returnCode")?null:returnMap.get("returnCode").toString();
                String returnDesc = null==returnMap.get("returnDesc")?null:returnMap.get("returnDesc").toString();
                String returnFlag = null==returnMap.get("returnFlag")?null:returnMap.get("returnFlag").toString();
                order.setWmsReturnFlag(returnFlag);
                order.setWmsReturnCode(returnCode);
                order.setWmsReturnDesc(returnDesc);
                if ("0".equals(returnFlag)){
                    mailService.sendMail("调用wms出库单接口失败(需要手动下单)",jsonParam.toString(),toBusiness);
                }

            }catch (Exception e){
                mailService.sendMail("调用wms出库单异常(需要手动下单)",jsonParam.toString(),toBusiness);
                log.info("调用wms出库单异常(需要手动下单)");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                e.printStackTrace(new PrintStream(baos));
                log.error(baos.toString());
            }
        }

        //对单子进行持久化
        //加入id
        HengruiTestInfo testInfo=order.getTestInfo();
        String id = UUID.randomUUID().toString();
        order.setId(id);
        hengruiOrderService.save(order);
        for (HengruiDetail detail : Details) {
            detail.setHdId(id);
        }
        hengruiDetailService.saveBatch(Details);
        if (null!=testInfo){
            testInfo.setHdId(id);
            hengruiTestInfoService.save(testInfo);
        }
        cimcMap.put("code","200".equals(code)?0:500005);

        return new AjaxResult(cimcMap);
    }
    /**
     * 运单创建接口
     */
    @RequestMapping(value="/cancelOrder",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> cancelOrder(@RequestParam Map<String, Object> params, HttpServletRequest request)  {
        //参数约定从body传输，post
        JSONObject jsonParam = JSONUtils.getJSONParam(request);
        HengruiOrderCancel orderCancel = JSONObject.parseObject(String.valueOf(jsonParam), HengruiOrderCancel.class);
        //验证必填
        Assert.notNull(orderCancel,"无法获取参数");

        //调用中集
        Map<String, Object> cimcMap = hengruiService.cancelOrder(orderCancel);
        String code = cimcMap.get("code").toString();
        String msg = cimcMap.get("msg").toString();

        cimcMap.put("code","200".equals(code)||msg.contains("已发货完成")?0:500005);
        //1、返回数据( 0: 失败1： 待审核 2: 成功
        cimcMap.put("data","200".equals(code)?2:msg.contains("已发货完成")?1:0);
        cimcMap.put("syncTime",new Date().getTime());

        //数据持久化
        orderCancel.setCimcCode(code);
        orderCancel.setCimcMsg(msg);
        hengruiOrderCancelService.save(orderCancel);
        return cimcMap;
    }

    /**
     * 运输节点回传
     */
    @RequestMapping(value="/returnNode",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AjaxResult returnNode(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException, ParseException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //参数约定从body传输，post
        JSONObject jsonParam = JSONUtils.getJSONParam(request);
        CimcNode cimcNode=JSONObject.parseObject(String.valueOf(jsonParam), CimcNode.class);

        Map<String, Object> retMap;
        String billcode = cimcNode.getBillCode();

        //验证节点是否映射
        SysDataMapping sysDataMapping= SystemCache.getSysDataMappingByCimcCode(billcode,"nodeCode");

        if (null != sysDataMapping && 1 == sysDataMapping.getTransmit()) {
            cimcNode.setCustomerCode(sysDataMapping.getCustomerCode());

            retMap=hengruiService.returnNode(cimcNode);
        }else {
            retMap=new HashMap<>();
            retMap.put("code","200");
            retMap.put("msg","此节点未对应客户节点，无需传输。");
        }
        return new AjaxResult(retMap);
    }
}
