package com.cimc.order.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 * @TableName sys_log
 */
@TableName(value ="sys_log")
@Data
public class SysLog implements Serializable {
    /**
     * 唯一主键
     */
    private Long id;

    /**
     * 客户项目代码
     */
    private String projectCode;

    /**
     * 接口key
     */
    private String interfaceCode;

    /**
     * 参数
     */
    private String operParam;

    /**
     * 响应
     */
    private String retResult;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}