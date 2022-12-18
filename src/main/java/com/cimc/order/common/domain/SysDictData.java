package com.cimc.order.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典数据表
 * @TableName sys_dict_data
 */
@TableName(value ="sys_dict_data")
@Data
public class SysDictData implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户项目代码
     */
    private String projectCode;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典键
     */
    private String dictKey;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 是否默认（1是 0否）
     */
    private Integer isDefault;

    /**
     * 状态（1正常 0停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 字典排序
     */
    private Integer dictSort;

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