package com.cimc.order.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_data_mapping
 */
@TableName(value ="sys_data_mapping")
@Data
public class SysDataMapping implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户项目代码
     */
    private String projectCode;

    /**
     * 映射类型区分同一项目的不同类型映射
     */
    private String mappingType;

    /**
     * 中集数据代码
     */
    private String cimcCode;

    /**
     * 中集数据名称
     */
    private String cimcValue;

    /**
     * 中集数据描述
     */
    private String cimcDesc;

    /**
     * 客户数据代码
     */
    private String customerCode;

    /**
     * 客户数据名称
     */
    private String customerValue;

    /**
     * 客户数据描述
     */
    private String customerDesc;

    /**
     * 是否传输（1正常 0不传输）
     */
    private Integer transmit;

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