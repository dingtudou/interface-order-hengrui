package com.cimc.order.hengrui.common;


import com.cimc.order.common.cache.SystemCache;
import com.cimc.order.common.domain.SysDataMapping;
import com.cimc.order.common.utils.DateUtils;
import com.cimc.order.common.utils.StringUtils;
import com.cimc.order.hengrui.domain.HengruiDetail;
import com.cimc.order.hengrui.domain.HengruiOrder;
import com.cimc.order.hengrui.domain.HengruiOrderCancel;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author: dingyongxin
 * @create: 2021-07-21 09:24
 **/

@Slf4j
@Component
public class CimcTmsUtil {
    public static void main(String[] args) {
        System.out.println("2022-12-31 23:59".length());
    }
    /**
     * 调用中集转单 参数转换
     */
    public  Map<String, Object> getChangeOrderParams(HengruiOrder order, SysDataMapping sysDataMapping) throws ParseException {
        List<HengruiDetail> Details=order.getDetailList();
        //货物名称
        String cargoName = SystemCache.sysParamMap.get("cargoName");
        //客户账号
        String accountNumber=SystemCache.sysParamMap.get("accountNumber");
        //取件时间
        String orderTime=SystemCache.sysParamMap.get("orderTime");
        //是否使用温度计 /温湿度计	 1、温度计 2、温湿度计 0.不使用
        String iswdj = "0".equals(order.getIstemperature())?"不使用":"使用";

        //处理货物类型（空/0.药品 1.样本）
        String goodstype="药品";
        String type=order.getType();
        if (StringUtils.isNotBlank(type)||"1".equals(type)){
            goodstype="样本";
        }


        //是否投保
        String safeitem = "不投保";
        //投保金额(投保时必传)
        String insuredAmount=StringUtils.trim(order.getInsuredAmount());
        Double safepay =NumberUtils.toDouble(insuredAmount);
        if (safepay>0){
            safeitem = "投保";
        }

        //要求取件时间客戶系统会下发此字段，但是可能为空（为空时默认为次日的上午9时
        String  sendTime = order.getRequireSendTime();
        String  getTime = order.getRequireGetTime();
        sendTime=StringUtils.isNotBlank(sendTime)&&sendTime.length()==16?sendTime+":00":sendTime;
        getTime=StringUtils.isNotBlank(getTime)&&getTime.length()==16?getTime+":00":getTime;

        if (StringUtils.isBlank(sendTime)){
            sendTime= DateUtils.formatDay(DateUtils.addDays(new Date(),1))+" "+orderTime;
        }
        //处理时限
        String limittime="48H";
        if (StringUtils.isNotBlank(getTime)){
            limittime =(DateUtils.parseDateMs(getTime).getTime() - DateUtils.parseDateMs(sendTime).getTime())/(60*60*1000)+"H";
        }

        //收发件省份城市
        Map<String,String> sendAreMap= AddressUtils.addressResolution(order.getSendArea());
        String depart=sendAreMap.get("province");
        String city=sendAreMap.get("city");
        Map<String,String> receiverAreaMap= AddressUtils.addressResolution(order.getReceiverArea());
        String getDepart=receiverAreaMap.get("province");
        String getCity=receiverAreaMap.get("city");

        //处理备注
        String note="";
        if (StringUtils.isNotBlank(order.getRemark())){
            note=order.getRemark()+"#";
        }
        if ("2".equals(order.getIstemperature())){
            note=note+"客户要求使用温湿度计"+"#";
        }
        if (StringUtils.isNotBlank(getTime)){
            note=note+"要求到货时间:"+order.getRequireGetTime()+"#";
        }
        if (StringUtils.isNotBlank(order.getNucleinRequirement())){
            note=note+"核酸要求说明:"+order.getNucleinRequirement()+"#";
        }
        //（药品名称+批号+数量）  这样是不是就行了
        Map<String, Integer> noteMap = new HashMap<>();
        for (HengruiDetail detail:Details) {
            String lotNo = "名称:"+detail.getProductName()+";批次:"+detail.getLotNo();
            Integer count=noteMap.get(lotNo);
            if (null==count){
                noteMap.put(lotNo,detail.getCount());
            }else {
                noteMap.put(lotNo,count+detail.getCount());
            }
        }
        for (Map.Entry<String,Integer> entry:noteMap.entrySet()){
            note=note+","+"("+entry.getKey()+";数量:"+entry.getValue()+")";
        }


        Map<String, Object> params = Maps.newHashMap();
        params.put("AccountNumber",accountNumber);                //客户账号
        params.put("GetCompany",order.getReceiverCompany());      //收货公司
        params.put("GetAddress",order.getReceiverAddress());      //收货地址
        params.put("GetName", order.getReceiver());               //收货人
        params.put("GetTelephone",order.getReceiverPhone());      //收货人手机
        params.put("GetDepart",getDepart);      //收货省份
        params.put("GetCity",getCity);      //收货城市

        params.put("Company",order.getSendCompany());      //发货单位
        params.put("Address",order.getSendAddress());      //发货地址
        params.put("Depart",depart);      //发货省份
        params.put("City",city);      //发货城市
        params.put("Manager",order.getSender());      //发货人
        params.put("Telephone",order.getSenderPhone());      //发货人手机

        params.put("Note",note); //备注
        //params.put("Box",boxList);      //包材信息数组
        params.put("EntryName",order.getInputOrderPerson());      //下单人姓名或账号
        params.put("SafeItem",safeitem);      //是否投保
        params.put("SafePay",safepay.toString());      //投保金额(投保时必传)
        params.put("IsWdj",iswdj);      //是否使用温度计
        params.put("WDQJ",sysDataMapping.getCimcValue());      //温度区间
        params.put("OrderTime",getTime);      //要求取件时间

        params.put("CargoName",cargoName);      //货物名称
        params.put("BillNo",order.getClientOrder());      //出库单号
        params.put("GoodsType",goodstype);      //货物类型
        //params.put("Goods",null);      //货物明细
        params.put("LimitTime",limittime);      //时限
        enptyChange(params);
        return  params;
    }
    /**
     * @description 取消订单
     **/
    public  Map<String, Object>   getcancelOrderParams(HengruiOrderCancel orderCancel){
        //客户账号
        String accountNumber=SystemCache.sysParamMap.get("accountNumber");
        //中集key
        String token=SystemCache.sysParamMap.get("token");

        Map<String, Object> params = Maps.newHashMap();
        //客户账号
        params.put("AccountNumber",accountNumber);
        //秘钥中集给出
        params.put("Token",token);
        //中集订单号
        params.put("OrderId",orderCancel.getLogisticsOrderNo());
        //取消原因
        params.put("Content",StringUtils.isNotBlank(orderCancel.getCancelReason())?orderCancel.getCancelReason():"接口取消");
        //取消人
        params.put("CancelName",orderCancel.getCancelOrderPerson());
        return  params;
    }
    public  Map<String, Object>  enptyChange(Map<String, Object> params) {
        StringBuffer note=new StringBuffer();
        if (StringUtils.isEmpty((String)params.get("GetAddress"))){
            params.put("GetAddress","暂无");
            note.append("收货地址为空");
        }
        if (StringUtils.isEmpty((String)params.get("GetName"))){
            params.put("GetName","暂无");
            note.append("收货人为空");
        }
        if (StringUtils.isEmpty((String)params.get("GetTelephone"))){
            params.put("GetTelephone","暂无");
            note.append("收货人手机为空");
        }
        if (StringUtils.isEmpty((String)params.get("GetDepart"))){
            params.put("GetDepart","暂无");
            note.append("收货省份为空");
        }
        if (StringUtils.isEmpty((String)params.get("GetCity"))){
            params.put("GetCity","暂无");
            note.append("收货城市为空");
        }
        if (StringUtils.isEmpty((String)params.get("Company"))){
            params.put("Company","暂无");
            note.append("发货单位为空");
        }
        if (StringUtils.isEmpty((String)params.get("Address"))){
            params.put("Address","暂无");
            note.append("发货地址为空");
        }
        if (StringUtils.isEmpty((String)params.get("Depart"))){
            params.put("Depart","暂无");
            note.append("发货省份为空");
        }
        if (StringUtils.isEmpty((String)params.get("City"))){
            params.put("City","暂无");
            note.append("发货城市为空");
        }
        if (StringUtils.isEmpty((String)params.get("Manager"))){
            params.put("Manager","暂无");
            note.append("发货人为空");
        }
        if (StringUtils.isEmpty((String)params.get("Telephone"))){
            params.put("Telephone","暂无");
            note.append("发货人手机为空");
        }
        if (StringUtils.isEmpty((String)params.get("EntryName"))){
            params.put("EntryName","暂无");
            note.append("下单人为空");
        }
        if (StringUtils.isEmpty((String)params.get("SafeItem"))){
            params.put("SafeItem","暂无");
            note.append("是否投保为空");
        }
        if (StringUtils.isEmpty((String)params.get("SafePay"))){
            params.put("SafePay","暂无");
            note.append("投保金额为空");
        }
        if (StringUtils.isEmpty((String)params.get("IsWdj"))){
            params.put("IsWdj","否");
            note.append("是否使用温度计为空默认填充否");
        }
        /*if (StringUtils.isEmpty((String)params.get("WDQJ"))){
            params.put("WDQJ","暂无");
            note.append("温度区间为空");
        }    */
        /*if (StringUtils.isEmpty((String)params.get("OrderTime"))){
            String orderTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd") + " 15:00:00";
            params.put("OrderTime",orderTime);
            note.append("取件时间为空默认填充今天下午3点");
        }
        if (StringUtils.isEmpty((String)params.get("GoodsType"))){
            params.put("GoodsType","暂无");
            note.append("货物类型为空");
        }

        params.put("Note",params.get("Note")+note.toString()); //备注*/
        return params;
    }



}
