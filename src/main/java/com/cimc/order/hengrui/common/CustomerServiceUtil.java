package com.cimc.order.hengrui.common;

import com.cimc.order.common.cache.SystemCache;
import com.cimc.order.common.utils.DateUtils;
import com.cimc.order.hengrui.domain.CimcNode;
import com.cimc.order.hengrui.domain.HengruiOrder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

/**
 * @description
 * @author: dingyongxin
 * @email 1358547120@qq.com
 * @create: 2021-11-11 10:51
 **/
@Component
public class CustomerServiceUtil {
    /**
     * 路由回传接口
     */
    public Map<String, Object> getReturnNodeParam(CimcNode cimcNode, HengruiOrder order, String xino) throws ParseException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //AES秘钥
        String  aes_key = SystemCache.sysParamMap.get("aes_key");

        //时间戳
        String syncTime= String.valueOf(new Date().getTime()/1000);
        //签名
        String signature=encrypt(DigestUtils.md5Hex(xino+"_"+syncTime),aes_key);
        //是否签收
        String billcode= cimcNode.getBillCode();
        String signedFlag="OK".equals(billcode)?"1":"0";
        //接受者城市
        Map<String,String> receiverAreaMap= AddressUtils.addressResolution(order.getReceiverArea());
        String receiveCity=receiverAreaMap.get("city");
        //receiveCity="city";


        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> logisticsOrderList = new ArrayList<>();
        Map<String, Object> logisticsOrder = new HashMap<>();

        List<Map<String, Object>> logisticsHistoryList = new ArrayList<>();
        Map<String, Object> logisticsHistory = new HashMap<>();



        //operationDate	number	必须	操作时间（时间戳）
        logisticsHistory.put("operationDate", DateUtils.parseDateMs(cimcNode.getTime()).getTime()/1000);
        //operationType	string	必须	操作类型	1 接单 2运输 3签收
        logisticsHistory.put("operationType", cimcNode.getCustomerCode());
        //operationCity	string	非必须	操作城市
        logisticsHistory.put("operationCity", cimcNode.getCity());
        //stopCity	string	非必须	发向城市
        //logisticsHistory.put("stopCity", cimcNode.getCustomerValue());
        //remark	string	非必须	备注
        logisticsHistory.put("remark", cimcNode.getBillNote());

        //logisticsHistory	object []	必须	物流信息	item 类型:    object
        logisticsHistoryList.add(logisticsHistory);

        logisticsOrder.put("logisticsHistory", logisticsHistoryList);
        //logisticsOrderNo	string	必须	物流单id
        logisticsOrder.put("logisticsOrderNo",cimcNode.getBillNumber());
        //sendCity	string	必须	发货城市
        logisticsOrder.put("sendCity",cimcNode.getCity());

        //receiveCity	string	必须	接受者城市
        logisticsOrder.put("receiveCity",receiveCity);

        //signedFlag	string	必须	是否签收	0未签收  1 签收
        logisticsOrder.put("signedFlag",signedFlag);



        //logisticsOrderList	object[]	必须	物流单列表
        logisticsOrderList.add(logisticsOrder);

        params.put("logisticsOrderList", logisticsOrderList);
        //shippingOrder	string	必须	订单号
        params.put("shippingOrder", xino);
        //signature	string	必须	签名,($shippingOrder _$syncTime)md5后aes加密，测试环境密码：hrcdms@#%123456!
        params.put("signature", signature);
        //syncTime	string	必须	时间（时间戳）
        params.put("syncTime", syncTime);

        return params;
    }

    /**
     * 回单照片同步接口
     */
    public Map<String, Object> getRreturnReceiptParam(CimcNode cimcNode , String xino) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //AES秘钥
        String  aes_key = SystemCache.sysParamMap.get("aes_key");

        //时间戳
        String syncTime= String.valueOf(new Date().getTime()/1000);
        //签名
        String signature=encrypt(DigestUtils.md5Hex(xino+"_"+syncTime),aes_key);

        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> logisticsFileList = new ArrayList<>();
        Map<String, Object> logisticsFile = new HashMap<>();

        List<Map<String, Object>> fileList = new ArrayList<>();

        List<Map<String, String>> pictures= cimcNode.getPicture();
        if (null!=pictures){
            for (Map<String, String> picture:pictures) {
                Map<String, Object> file = new HashMap<>();
                //fileUrl	string	必须	文件url
                file.put("fileUrl", picture.get("FileUrl"));
                //中集  交接单=WLJJD 温度记录=WDJL 物流其他文件=OTHER
                //客户   fileType number	必须	文件类型  1 温度记录 2 交接单 3 其他
                file.put("fileType",SystemCache.getSysDataMappingByCimcCode(picture.get("Type"),"fileType").getCustomerCode());
                //remark	string	非必须	备注
                //file.put("remark", remark);

                //fileList	object []	必须	文件列表
                fileList.add(file);
            }
        }
        //logisticsOrderNo	string	必须	物流单id
        logisticsFile.put("logisticsOrderNo",cimcNode.getBillNumber());
        //remark	string	非必须	备注
        logisticsFile.put("remark", cimcNode.getBillNote());
        //文件列表
        logisticsFile.put("fileList", fileList);

        //logisticsFileList	object[]	必须	物流单列表
        logisticsFileList.add(logisticsFile);

        //物流单列表
        params.put("logisticsFileList", logisticsFileList);
        //shippingOrder	string	必须	订单号
        params.put("shippingOrder", xino);
        //signature	string	必须	签名,($shippingOrder _$syncTime)md5后aes加密，测试环境密码：hrcdms@#%123456!
        params.put("signature", signature);
        //syncTime	string	必须	时间（时间戳）
        params.put("syncTime", syncTime);
        //remark	string	非必须	备注
        //params.put("remark", remark);

        return params;
    }

    /**
     * 加密
     *
     * @param content
     * @param key
     * @return
     */
    public static String encrypt(String content, String key) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] raw;
        raw = key.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        /**
         * "算法/模式/补码方式"
         */
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
        return new Base64().encodeToString(encrypted);

    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String data= DigestUtils.md5Hex( "INS2022090005_1663222424");
        String aes_key="hrcdms@#%123456!";
        System.out.println(data);
        System.out.print(encrypt(data, aes_key));
    }

    /**
     * 参数imgFile：图片完整路径
     * @param imgUrl
     * @throws IOException
     */
    public static String getImgBase64Str(String imgUrl) throws IOException {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String tmp=System.getProperty("java.io.tmpdir");
        System.out.println(tmp);
        File fileDir=new File(tmp+File.separator+"img");
        if(!fileDir.exists()){
            fileDir.mkdir();
        };

        UrlResource urlResource=new UrlResource(imgUrl);
        InputStream in = urlResource.getInputStream();
        String tmpFile=tmp+File.separator+"img"+File.separator+urlResource.getFilename();
        File file = new File(tmpFile);
        if (!file.exists()){
            Files.copy(in, Paths.get(tmpFile));
        }

        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }
}