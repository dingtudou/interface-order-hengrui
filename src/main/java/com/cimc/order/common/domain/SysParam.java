package com.cimc.order.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统全局参数配置表
 * @TableName sys_param
 */
@TableName(value ="sys_param")
@Data
public class SysParam implements Serializable {
    /**
     * 唯一主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户项目代码
     */
    private String projectCode;

    /**
     * 定义key
     */
    private String code;

    /**
     * 定义value
     */
    private String value;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态（1正常 0停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

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