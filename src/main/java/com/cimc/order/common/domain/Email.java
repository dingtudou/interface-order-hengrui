package com.cimc.order.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @description
 * @author: dingyongxin
 * @email 1358547120@qq.com
 * @create: 2021-07-22 10:58
 **/
@Data //该注解会自动生成 getter setter 方法
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    private String content;// 邮件内容文本

    // 以模板发送邮件的时候填写以下参数的值
    private String template;// 邮件模板
    private HashMap<String, Object> kvMap;// 自定义参数，用于模板发送时显示内容


}
