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
 * @TableName sys_interface
 */
@TableName(value ="sys_interface")
@Data
public class SysInterface implements Serializable {
    /**
     * 唯一ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 客户项目代码
     */
    private String projectCode;

    /**
     * 主键
     */
    private String code;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPwd;

    /**
     * 命名空间
     */
    private String nameSpace;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数名
     */
    private String paramName;

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