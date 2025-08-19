package com.atguigu.lease.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Schema(description = "公寓标签关联表")
@TableName(value = "apartment_label")
//@Data
@Builder
public class ApartmentLabel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "公寓id")
    @TableField(value = "apartment_id")
    private Long apartmentId;

    @Schema(description = "标签id")
    @TableField(value = "label_id")
    private Long labelId;


    public ApartmentLabel() {
    }

    public ApartmentLabel(Long apartmentId, Long labelId) {
        this.apartmentId = apartmentId;
        this.labelId = labelId;
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
     * @return labelId
     */
    public Long getLabelId() {
        return labelId;
    }

    /**
     * 设置
     * @param labelId
     */
    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String toString() {
        return "ApartmentLabel{serialVersionUID = " + serialVersionUID + ", apartmentId = " + apartmentId + ", labelId = " + labelId + "}";
    }
}