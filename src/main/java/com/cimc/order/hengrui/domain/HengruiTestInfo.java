package com.cimc.order.hengrui.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName hengrui_test_info
 */
@TableName(value ="hengrui_test_info")
@Data
public class HengruiTestInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String hdId;

    /**
     * 临床试验题目
     */
    private String topicsOfClinicalTrials;

    /**
     * 研究编号
     */
    private String researchNumber;

    /**
     * 试验中心
     */
    private String testCenter;

    /**
     * 试验中心编号
     */
    private String testCenterNumber;

    /**
     * 研究者
     */
    private String researcher;

    /**
     * 申办者
     */
    private String sponsor;

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