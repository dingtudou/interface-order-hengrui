package com.cimc.order.common.domain;

import com.cimc.order.common.constant.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作消息提醒
 * 
 * @author ruoyi
 */
public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";
    /** 返回内容 */
    public static final String MSG_TAG = "msg";
    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * 
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);

    }
    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param map
     */
    public AjaxResult(Map<String, Object> map){
        int code = 400;
        String msg = "";
        String orderid = "";
        if (null!=map){
            Object codeObt = map.get("code");
            Object msgObt = map.get("msg");
            Object orderidObt = map.get("OrderId");
            code = Integer.parseInt(codeObt.toString());
            msg= String.valueOf(msgObt);
            orderid=String.valueOf(orderidObt);
        }
        super.put(MSG_TAG, msg);
        super.put(CODE_TAG, code);
        super.put(DATA_TAG, orderid);
    }
    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param map
     */
    public AjaxResult(Map<String, Object> map,String CODE_TAG,String MSG_TAG){
        int code = 400;
        String msg = "";
        if (null!=map){
            Object codeObt = map.get("code");
            Object msgObt = map.get("msg");
            code = Integer.parseInt(codeObt.toString());
            msg= String.valueOf(msgObt);
        }
        super.put(MSG_TAG, msg);
        super.put(CODE_TAG, 200==code?0:-1);
    }
    /**
     * 初始化一个新创建的 AjaxResult 对象
     */
    public AjaxResult(int code,String msg,String CODE_TAG,String MSG_TAG){
        super.put(MSG_TAG, msg);
        super.put(CODE_TAG, code);
    }
    /**
     * 初始化一个新创建的 AjaxResult 对象
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (null!=data)
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     * 
     * @return 成功消息
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     * 
     * @return 成功消息
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     * 
     * @return
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }
}
