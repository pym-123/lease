package com.atguigu.lease.model.entity;

import com.atguigu.lease.model.enums.ReleaseStatus;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "公寓信息表")
@TableName(value = "apartment_info")
//@Data
public class ApartmentInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "公寓名称")
    @TableField(value = "name")
    private String name;

    @Schema(description = "公寓介绍")
    @TableField(value = "introduction")
    private String introduction;

    @Schema(description = "所处区域id")
    @TableField(value = "district_id")
    private Long districtId;

    @Schema(description = "所处区域名称")
    @TableField(value = "district_name")
    private String districtName;

    @Schema(description = "所处城市id")
    @TableField(value = "city_id")
    private Long cityId;

    @Schema(description = "所处城市名称")
    @TableField(value = "city_name")
    private String cityName;

    @Schema(description = "所处省份id")
    @TableField(value = "province_id")
    private Long provinceId;

    @Schema(description = "所处区域名称")
    @TableField(value = "province_name")
    private String provinceName;

    @Schema(description = "详细地址")
    @TableField(value = "address_detail")
    private String addressDetail;

    @Schema(description = "经度")
    @TableField(value = "latitude")
    private String latitude;

    @Schema(description = "纬度")
    @TableField(value = "longitude")
    private String longitude;

    @Schema(description = "公寓前台电话")
    @TableField(value = "phone")
    private String phone;

    @Schema(description = "是否发布")
    @TableField(value = "is_release")
    private ReleaseStatus isRelease;


    public ApartmentInfo() {
    }

    public ApartmentInfo(String name, String introduction, Long districtId, String districtName, Long cityId, String cityName, Long provinceId, String provinceName, String addressDetail, String latitude, String longitude, String phone, ReleaseStatus isRelease) {
        this.name = name;
        this.introduction = introduction;
        this.districtId = districtId;
        this.districtName = districtName;
        this.cityId = cityId;
        this.cityName = cityName;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.addressDetail = addressDetail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.isRelease = isRelease;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置
     * @param introduction
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取
     * @return districtId
     */
    public Long getDistrictId() {
        return districtId;
    }

    /**
     * 设置
     * @param districtId
     */
    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    /**
     * 获取
     * @return districtName
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * 设置
     * @param districtName
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * 获取
     * @return cityId
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * 设置
     * @param cityId
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * 获取
     * @return cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 设置
     * @param cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * 获取
     * @return provinceId
     */
    public Long getProvinceId() {
        return provinceId;
    }

    /**
     * 设置
     * @param provinceId
     */
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * 获取
     * @return provinceName
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * 设置
     * @param provinceName
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * 获取
     * @return addressDetail
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * 设置
     * @param addressDetail
     */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    /**
     * 获取
     * @return latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置
     * @param latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取
     * @return longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取
     * @return isRelease
     */
    public ReleaseStatus getIsRelease() {
        return isRelease;
    }

    /**
     * 设置
     * @param isRelease
     */
    public void setIsRelease(ReleaseStatus isRelease) {
        this.isRelease = isRelease;
    }

    public String toString() {
        return "ApartmentInfo{serialVersionUID = " + serialVersionUID + ", name = " + name + ", introduction = " + introduction + ", districtId = " + districtId + ", districtName = " + districtName + ", cityId = " + cityId + ", cityName = " + cityName + ", provinceId = " + provinceId + ", provinceName = " + provinceName + ", addressDetail = " + addressDetail + ", latitude = " + latitude + ", longitude = " + longitude + ", phone = " + phone + ", isRelease = " + isRelease + "}";
    }
}