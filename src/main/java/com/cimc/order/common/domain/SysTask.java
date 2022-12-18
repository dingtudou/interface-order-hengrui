package com.cimc.order.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务计划表
 * @TableName sys_task
 */
@TableName(value ="sys_task")
@Data
public class SysTask implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户项目代码
     */
    private String projectCode;

    /**
     * 任务名
     */
    private String jobName;

    /**
     * 任务分组
     */
    private String jobGroup;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String beanClass;

    /**
     * 任务调用的方法名
     */
    private String methodName;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务是否有状态
     */
    private String concurrent;

    /**
     * Spring bean
     */
    private String springBean;

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