package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "公寓&配套关系")
@TableName(value = "apartment_facility")
//@Data
@Builder
public class ApartmentFacility extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "公寓id")
    @TableField(value = "apartment_id")
    private Long apartmentId;

    @Schema(description = "设施id")
    @TableField(value = "facility_id")
    private Long facilityId;


    public ApartmentFacility() {
    }

    public ApartmentFacility(Long apartmentId, Long facilityId) {
        this.apartmentId = apartmentId;
        this.facilityId = facilityId;
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
     * @return facilityId
     */
    public Long getFacilityId() {
        return facilityId;
    }

    /**
     * 设置
     * @param facilityId
     */
    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public String toString() {
        return "ApartmentFacility{serialVersionUID = " + serialVersionUID + ", apartmentId = " + apartmentId + ", facilityId = " + facilityId + "}";
    }
}