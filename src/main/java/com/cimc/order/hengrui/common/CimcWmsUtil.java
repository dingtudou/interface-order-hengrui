package com.cimc.order.hengrui.common;


import com.cimc.order.common.cache.SystemCache;
import com.cimc.order.common.domain.SysDataMapping;
import com.cimc.order.common.utils.ArrayUtils;
import com.cimc.order.common.utils.DateUtils;
import com.cimc.order.common.utils.StringUtils;
import com.cimc.order.hengrui.domain.HengruiDetail;
import com.cimc.order.hengrui.domain.HengruiOrder;
import com.cimc.order.hengrui.domain.HengruiTestInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

/**
 * @description
 * @author: dingyongxin
 * @create: 2022-11-09
 **/

@Slf4j
@Component
public class CimcWmsUtil {

    /**
     * @description: 出库单下发至wms
     **/
    public  Map<String, Object> getWmsUrlParams(String method,String messageId) {
        String appToken = SystemCache.sysParamMap.get("appToken");
        String sign = SystemCache.sysParamMap.get("sign");
        String clientCustomerid = SystemCache.sysParamMap.get("clientCustomerid");
        String clientDb = SystemCache.sysParamMap.get("clientDb");
        String timestamp=DateUtils.formatMs(new Date());

        Map<String, Object> params = Maps.newHashMap();
        params.put("apptoken",appToken);
        params.put("timestamp",timestamp);
        params.put("sign",sign);
        params.put("client_customerid",clientCustomerid);
        params.put("client_db",clientDb);
        params.put("method",method);
        params.put("messageid",messageId);

        return params;
    }
    /**
     * @description: 出库单下发至wms
     **/
    public  Map<String, Object> getPutSalesOrderParams(HengruiOrder order)   {
        //仓库ID
        String warehouseId = SystemCache.sysParamMap.get("warehouseId");
        //货主ID
        String customerId=SystemCache.sysParamMap.get("customerId");

        //处理客户时间标准问题
        String  requireSendTime = order.getRequireSendTime();
        String  requireGetTime = order.getRequireGetTime();
        requireSendTime=requireSendTime.length()==19?requireSendTime:requireSendTime+":00";
        requireGetTime=requireGetTime.length()==19?requireGetTime:requireGetTime+":00";

        List<HengruiDetail> detailList=order.getDetailList();
        HengruiTestInfo testInfo = order.getTestInfo();

        Map<String, Object> params = Maps.newHashMap();
        Map<String, Object> data = Maps.newHashMap();
        params.put("data",data);
        List<Map<String, Object>> header =new ArrayList<>();
        data.put("header",header);
        Map<String, Object> headerItem = Maps.newHashMap();
        header.add(headerItem);

        //warehouseId	string(20)	TRUE	仓库ID	默认BJ01
        headerItem.put("warehouseId",warehouseId);
        //customerId	string(30)	TRUE	货主ID	默认HRYY
        headerItem.put("customerId",customerId);
        //orderType	String(20)	TRUE	订单类型	默认XS
        headerItem.put("orderType","XS");
        //docNo	String(21)	TRUE	上游订单号	ClientOrder运单号
        headerItem.put("docNo",order.getClientOrder());
        //soReferenceA	String(50)	FALSE	销售订单号	tms的orderid
        headerItem.put("soReferenceA",order.getCimcOrderid());
        //orderTime	datetime	FALSE	订单创建时间	InputOrderTime下单时间
        headerItem.put("orderTime",DateUtils.formatMs(new Date()));
        //expectedShipmentTime1	datetime	FALSE	预期发货时间	RequireSendTime要求取件时间
        headerItem.put("expectedShipmentTime1",requireSendTime);
        //requiredDeliveryTime	datetime	FALSE	要求交货时间	RequireGetTime要求到货时间
        headerItem.put("requiredDeliveryTime",requireGetTime);

        //consigneeId	String(50)	TRUE	收货人代码	随机 id
        //headerItem.put("consigneeId", UUID.randomUUID());
        headerItem.put("consigneeId", "002");


        //consigneeName	String(200)	TRUE	收货人名称	ReceiverCompany 收件单位（公司）
        //headerItem.put("consigneeName",order.getReceiverCompany());
        headerItem.put("consigneeName","安徽肿瘤医院");


        //consigneeContact	String(201)	TRUE	收货人联系人	Receiver收件人
        headerItem.put("consigneeContact",order.getReceiver());
        //consigneeAddress1	String(200)	TRUE	收货人地址 1	ReceiverAddress 收件地址
        headerItem.put("consigneeAddress1",order.getReceiverAddress());
        //consigneeCity	String(50)	FALSE	收货人城市	ReceiverArea 收件区域
        headerItem.put("consigneeCity",order.getReceiverArea());
        //consigneeTel1	String(50)	FALSE	收货人手机号	ReceiverPhone  收件电话
        headerItem.put("consigneeTel1",order.getReceiverPhone());
        //notes	String(500)	FALSE	备注	Remark  备注
        headerItem.put("notes",order.getRemark());
        //hedi01	String(200)	FALSE	EDI 信息 01	TopicsOfClinicalTrials 临床试验题目
        headerItem.put("hedi01",testInfo.getTopicsOfClinicalTrials());
        //hedi02	String(200)	FALSE	EDI 信息 02	ResearchNumber 研究编号
        headerItem.put("hedi02",testInfo.getResearchNumber());
        //hedi03	String(200)	FALSE	EDI 信息 03	TestCenter 试验中心
        headerItem.put("hedi03",testInfo.getTestCenter());
        //hedi04	String(200)	FALSE	EDI 信息 04	TestCenterNumber 试验中心编号
        String hedi04=testInfo.getTestCenterNumber();
        headerItem.put("hedi04",StringUtils.isNotBlank(hedi04)?hedi04.replace("\n",""):hedi04);
        //hedi05	String(200)	FALSE	EDI 信息 05	Researcher 研究者
        headerItem.put("hedi05",testInfo.getResearcher());
        //hedi06	String(200)	FALSE	EDI 信息 06	Sponsor 申办者
        headerItem.put("hedi06",testInfo.getSponsor());

        //出库单明细
        List<Map<String, Object>> details =new ArrayList<>();
        headerItem.put("details",details);

        if (null!=details){
            int count=0;
            for (HengruiDetail item:detailList) {

                String drBatchNumbers = StringUtils.isNotBlank(item.getDrBatchNumbers())?item.getDrBatchNumbers():"";
                String[] drBatchNumberArr=drBatchNumbers.split(",");
                Arrays.sort(drBatchNumberArr);
                String dedi04=drNumberCompartmentation(drBatchNumbers);
                for (String drBatchNumber:drBatchNumberArr){
                    count++;
                    Map<String, Object> detail = Maps.newHashMap();
                    details.add(detail);

                    //lineNo	int	TRUE	行号	随机从1开始增长
                    detail.put("lineNo",count);
                    //sku	String(50)	TRUE	产品	MainId 药品ID
                    //detail.put("sku",item.getMainId());
                    detail.put("sku",item.getMainId());
                    //qtyOrdered	decimal(18, 8)	TRUE	订货数	Count 数量
                    detail.put("qtyOrdered",drBatchNumberArr.length==1?item.getCount():1);
                    //notes	String(100)	FALSE	备注	Remark 备注
                    detail.put("notes",item.getRemark());
                    //lotAtt08	String(100)	FALSE	质量状态	默认N
                    detail.put("lotAtt08","N");
                    //lotAtt09	String(100)	FALSE	批次属性 09	DrBatchNumbers
                    detail.put("lotAtt09", drBatchNumber);
                    //lotAtt10	String(100)	FALSE	失效日期	TermValidity 有效期	暂时不传
                    detail.put("lotAtt10",item.getTermValidity());
                    //dedi04	String(200)	FALSE	EDI 信息 04	按照DrBatchNumbers最后几位是数字且是连续的我就体现成区间，其它之外的就组合显示。
                    detail.put("dedi04",dedi04);
                    //dedi05	String(200)	FALSE	批号/序列号	lotNo   批次号
                    detail.put("dedi05",item.getLotNo());

                }
            }
        }
        return  params;
    }

    /**
     * 药品编号区间化
     * @return
     */
    public static String drNumberCompartmentation(String str){
        List<String> drNumberCompars=new ArrayList<>();

        if(StringUtils.isNotBlank(str)){
            String[] arr=str.split(",");

            //是否全部不--不可区间化
            boolean isNotAllConsistent=false;

            List<List<String>> drBatchNumLists=new ArrayList<>();
            List<String> drBatchNumList=new ArrayList<>();
            drBatchNumList.add(arr[0]);
            drBatchNumLists.add(drBatchNumList);
            for (int i=1;i<arr.length;i++) {
                String suffix = StringUtils.getLastNumber(arr[i]);
                String prefix = arr[i].substring(0,arr[i].length()-suffix.length());
                String previousSuffix = StringUtils.getLastNumber(arr[i-1]);
                String previousPrefix = arr[i-1].substring(0,arr[i-1].length()-previousSuffix.length());

                //判断前缀是否一致
                boolean isCompar=prefix.equals(previousPrefix);
                //判断后缀是否数字
                isCompar=isCompar&&StringUtils.isNumeric(suffix)&&StringUtils.isNumeric(previousSuffix);
                //判断字符串是否连续
                isCompar=isCompar&&Integer.parseInt(suffix)==Integer.parseInt(previousSuffix)+1;
                isNotAllConsistent=isNotAllConsistent||isCompar;
                if (isCompar){
                    drBatchNumLists.get(drBatchNumLists.size()-1).add(arr[i]);
                }else {
                    List<String> list=new ArrayList<>();
                    list.add(arr[i]);
                    drBatchNumLists.add(list);
                }
            }
            if (!isNotAllConsistent){
                drNumberCompars.add(str);
            }else {
                for (List<String> list:drBatchNumLists) {
                    if (list.size()>1){
                        drNumberCompars.add(list.get(0)+"~"+list.get(list.size()-1));
                    }else {
                        drNumberCompars.add(list.get(0));
                    }
                }
            }
        }

        return StringUtils.join(drNumberCompars,",");
    }

    public static void main(String[] args) {
        String drBatchNumbers="BLTN-400mg-0119,BLTN-400mg-0112,BLTN-400mg-0113,BLTN-400mg-0114,BLTN-400mg-0115,BLTN-400mg-0116,BLTN-400mg-0117,BLTN-400mg-0118,BLTN-400mg-0119,BLTN-400mg-0120,BLTN-400mg-0121,BLTN-400mg-0122,BLTN-400mg-0123,BLTN-400mg-0124,BLTN-400mg-0125,BLTN-400mg-0126,BLTN-400mg-0127,BLTN-400mg-0128,BLTN-400mg-0129,BLTN-400mg-0130,BLTN-400mg-0131,BLTN-400mg-0132,BLTN-400mg-0133,BLTN-400mg-0134,BLTN-400mg-0135";


        /*String[] arr=drBatchNumbers.split(",");
        Arrays.sort(arr);
        for (String str:arr) {
            System.out.println(str);
        }*/
        //System.out.println(Integer.parseInt("0001"));

        //System.out.println(StringUtils.getLastNumber(drBatchNumbers));
        System.out.println(drNumberCompartmentation(drBatchNumbers));
    }
}
