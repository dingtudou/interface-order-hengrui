package com.cimc.order.hengrui.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName hengrui_detail
 */
@TableName(value ="hengrui_detail")
@Data
public class HengruiDetail implements Serializable {
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
     * 药品id
     */
    private String mainId;

    /**
     * 物品名称（药品名称）
     */
    private String productName;

    /**
     * 件数（药品数量）
     */
    private Integer packages;

    /**
     * 单位
     */
    private String unit;

    /**
     * 温度区间
     */
    private String temperatureRange;

    /**
     * 规格（如果接口类型是[空/0.药品]，则规格必传；如果接口类型是[1.样本]，则规格非必传）
     */
    private String specification;

    /**
     * 保存条件
     */
    private String drugSave;

    /**
     * 药品编号(有编号药品需要提供)多个用逗号分隔(例:”e00143,e00144,e00145”)
     */
    private String drBatchNumbers;

    /**
     * 备注
     */
    private String remark;

    /**
     * 批次号（如果接口类型是[空/0.药品]，则批次号必传；如果接口类型是[1.样本]，则批次号非必传）
     */
    private String lotNo;

    /**
     * 药品编号
     */
    private String drNumber;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 有效期（如果接口类型是[空/0.药品]，则有效期必传；如果接口类型是[1.样本]，则有效期非必传）
     */
    private String termValidity;

    /**
     * 样本类型（如果接口类型是[空/0.药品]，则样本类型非必传；如果接口类型是[1.样本]，则样本类型必传）
     */
    private String sampleType;

    /**
     * 货物长度(单位/ cm)（如果接口类型是[空/0.药品]，则非必传；如果接口类型是[1.样本]，则必传）
     */
    private String goodsLength;

    /**
     * 货物宽度(单位/ cm)（如果接口类型是[空/0.药品]，则非必传；如果接口类型是[1.样本]，则必传）
     */
    private String goodsWidth;

    /**
     * 货物高度(单位/ cm)（如果接口类型是[空/0.药品]，则非必传；如果接口类型是[1.样本]，则必传）
     */
    private String goodsHigh;

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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        HengruiDetail other = (HengruiDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHdId() == null ? other.getHdId() == null : this.getHdId().equals(other.getHdId()))
            && (this.getMainId() == null ? other.getMainId() == null : this.getMainId().equals(other.getMainId()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getPackages() == null ? other.getPackages() == null : this.getPackages().equals(other.getPackages()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getTemperatureRange() == null ? other.getTemperatureRange() == null : this.getTemperatureRange().equals(other.getTemperatureRange()))
            && (this.getSpecification() == null ? other.getSpecification() == null : this.getSpecification().equals(other.getSpecification()))
            && (this.getDrugSave() == null ? other.getDrugSave() == null : this.getDrugSave().equals(other.getDrugSave()))
            && (this.getDrBatchNumbers() == null ? other.getDrBatchNumbers() == null : this.getDrBatchNumbers().equals(other.getDrBatchNumbers()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getLotNo() == null ? other.getLotNo() == null : this.getLotNo().equals(other.getLotNo()))
            && (this.getDrNumber() == null ? other.getDrNumber() == null : this.getDrNumber().equals(other.getDrNumber()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getTermValidity() == null ? other.getTermValidity() == null : this.getTermValidity().equals(other.getTermValidity()))
            && (this.getSampleType() == null ? other.getSampleType() == null : this.getSampleType().equals(other.getSampleType()))
            && (this.getGoodsLength() == null ? other.getGoodsLength() == null : this.getGoodsLength().equals(other.getGoodsLength()))
            && (this.getGoodsWidth() == null ? other.getGoodsWidth() == null : this.getGoodsWidth().equals(other.getGoodsWidth()))
            && (this.getGoodsHigh() == null ? other.getGoodsHigh() == null : this.getGoodsHigh().equals(other.getGoodsHigh()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHdId() == null) ? 0 : getHdId().hashCode());
        result = prime * result + ((getMainId() == null) ? 0 : getMainId().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getPackages() == null) ? 0 : getPackages().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getTemperatureRange() == null) ? 0 : getTemperatureRange().hashCode());
        result = prime * result + ((getSpecification() == null) ? 0 : getSpecification().hashCode());
        result = prime * result + ((getDrugSave() == null) ? 0 : getDrugSave().hashCode());
        result = prime * result + ((getDrBatchNumbers() == null) ? 0 : getDrBatchNumbers().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getLotNo() == null) ? 0 : getLotNo().hashCode());
        result = prime * result + ((getDrNumber() == null) ? 0 : getDrNumber().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getTermValidity() == null) ? 0 : getTermValidity().hashCode());
        result = prime * result + ((getSampleType() == null) ? 0 : getSampleType().hashCode());
        result = prime * result + ((getGoodsLength() == null) ? 0 : getGoodsLength().hashCode());
        result = prime * result + ((getGoodsWidth() == null) ? 0 : getGoodsWidth().hashCode());
        result = prime * result + ((getGoodsHigh() == null) ? 0 : getGoodsHigh().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hdId=").append(hdId);
        sb.append(", mainId=").append(mainId);
        sb.append(", productName=").append(productName);
        sb.append(", packages=").append(packages);
        sb.append(", unit=").append(unit);
        sb.append(", temperatureRange=").append(temperatureRange);
        sb.append(", specification=").append(specification);
        sb.append(", drugSave=").append(drugSave);
        sb.append(", drBatchNumbers=").append(drBatchNumbers);
        sb.append(", remark=").append(remark);
        sb.append(", lotNo=").append(lotNo);
        sb.append(", drNumber=").append(drNumber);
        sb.append(", count=").append(count);
        sb.append(", termValidity=").append(termValidity);
        sb.append(", sampleType=").append(sampleType);
        sb.append(", goodsLength=").append(goodsLength);
        sb.append(", goodsWidth=").append(goodsWidth);
        sb.append(", goodsHigh=").append(goodsHigh);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}