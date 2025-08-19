package com.atguigu.lease.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "公寓&杂费关联表")
@TableName(value = "apartment_fee_value")
//@Data
@Builder
public class ApartmentFeeValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "公寓id")
    @TableField(value = "apartment_id")
    private Long apartmentId;

    @Schema(description = "收费项value_id")
    @TableField(value = "fee_value_id")
    private Long feeValueId;


    public ApartmentFeeValue() {
    }

    public ApartmentFeeValue(Long apartmentId, Long feeValueId) {
        this.apartmentId = apartmentId;
        this.feeValueId = feeValueId;
    }

    /**
     * 获取
     * @return apartmentId
     */
    public Long getApartmentId() {
        return apartmentId;
    }

    /**
     * 设置
     * @param apartmentId
     */
    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    /**
     * 获取
     * @return feeValueId
     */
    public Long getFeeValueId() {
        return feeValueId;
    }

    /**
     * 设置
     * @param feeValueId
     */
    public void setFeeValueId(Long feeValueId) {
        this.feeValueId = feeValueId;
    }

    public String toString() {
        return "ApartmentFeeValue{serialVersionUID = " + serialVersionUID + ", apartmentId = " + apartmentId + ", feeValueId = " + feeValueId + "}";
    }
}