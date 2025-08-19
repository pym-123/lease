package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.web.admin.mapper.ApartmentInfoMapper;
import com.atguigu.lease.web.admin.mapper.GraphInfoMapper;
import com.atguigu.lease.web.admin.mapper.RoomInfoMapper;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.atguigu.lease.web.admin.vo.fee.FeeValueVo;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import static com.atguigu.lease.model.enums.ItemType.APARTMENT;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;

    @Autowired
    private ApartmentLabelService apartmentLabelService;

    @Autowired
    private LabelInfoService labelInfoService;

    @Autowired
    private ApartmentFacilityService apartmentFacilityService;

    @Autowired
    private FacilityInfoService facilityInfoService;

    @Autowired
    private ProvinceInfoService provinceInfoService;

    @Autowired
    private CityInfoService cityInfoService;

    @Autowired
    private DistrictInfoService districtInfoService;

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
            apartmentSubmitVo.setProvinceName(provinceInfoService.getById(apartmentSubmitVo.getProvinceId()).getName());
            apartmentSubmitVo.setCityName(cityInfoService.getById(apartmentSubmitVo.getCityId()).getName());
            apartmentSubmitVo.setDistrictName(districtInfoService.getById(apartmentSubmitVo.getDistrictId()).getName());

            super.saveOrUpdate(apartmentSubmitVo);

            if(apartmentSubmitVo.getId()!=null){
                //删除公寓图片
                LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();

                graphQueryWrapper.eq(GraphInfo::getItemType, APARTMENT);

                graphQueryWrapper.eq(GraphInfo::getItemId,apartmentSubmitVo.getId());

                graphInfoService.remove(graphQueryWrapper);

                //删除公寓杂费
                LambdaQueryWrapper<ApartmentFeeValue> feeValueLambdaQueryWrapper  = new LambdaQueryWrapper<>();

                feeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId,apartmentSubmitVo.getId());

                apartmentFeeValueService.remove(feeValueLambdaQueryWrapper);

                //删除公寓标签
                LambdaQueryWrapper<ApartmentLabel> apartmentLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();

                apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,apartmentSubmitVo.getId());

                apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);

                //删除公寓配套
                LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();

                apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,apartmentSubmitVo.getId());

                apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);

                //更新图片
                if(!CollectionUtils.isEmpty(apartmentSubmitVo.getGraphVoList())){

                    List<GraphInfo> list = new ArrayList<>();

                    for(GraphVo graphVo : apartmentSubmitVo.getGraphVoList()){
                        GraphInfo graphInfo = new GraphInfo();

                        graphInfo.setName(graphVo.getName());

                        graphInfo.setUrl(graphVo.getUrl());

                        graphInfo.setItemId(apartmentSubmitVo.getId());

                        graphInfo.setItemType(APARTMENT);

                        list.add(graphInfo);
                    }

                    graphInfoService.saveOrUpdateBatch(list);
                }
                //更新公寓杂费
                if(!CollectionUtils.isEmpty(apartmentSubmitVo.getFeeValueIds())){
                    List<ApartmentFeeValue> list = new ArrayList<>();

                    for(Long feeValueId : apartmentSubmitVo.getFeeValueIds()){

                        ApartmentFeeValue apartmentFeeValue = new ApartmentFeeValue();

                        apartmentFeeValue.setApartmentId(apartmentSubmitVo.getId());

                        apartmentFeeValue.setFeeValueId(feeValueId);

                        list.add(apartmentFeeValue);
                    }

                    apartmentFeeValueService.saveOrUpdateBatch(list);
                }

                //更新标签
                if(!CollectionUtils.isEmpty(apartmentSubmitVo.getLabelIds())){
                    List<ApartmentLabel> list = new ArrayList<>();

                    for(Long apartmentLabelId : apartmentSubmitVo.getLabelIds()){

                        ApartmentLabel apartmentLabel  = new ApartmentLabel();

                        apartmentLabel.setApartmentId(apartmentSubmitVo.getId());

                        apartmentLabel.setLabelId(apartmentLabelId);

                        list.add(apartmentLabel);

                    }

                    apartmentLabelService.saveOrUpdateBatch(list);
                }

                //更新配套
                if(!CollectionUtils.isEmpty(apartmentSubmitVo.getFacilityInfoIds())){
                    List<ApartmentFacility> list = new ArrayList<>();

                    for(Long apartmentFacilityId : apartmentSubmitVo.getFacilityInfoIds()){
                        ApartmentFacility apartmentFacility = new ApartmentFacility();

                        apartmentFacility.setApartmentId(apartmentSubmitVo.getId());

                        apartmentFacility.setFacilityId(apartmentFacilityId);

                        list.add(apartmentFacility);

                    }
                    apartmentFacilityService.saveOrUpdateBatch(list);
                }
            }
    }

    @Override
    public IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> iPage, ApartmentQueryVo apartmentQueryVo) {
        return apartmentInfoMapper.selectPageApartmentItem(iPage,apartmentQueryVo);
    }

    @Override
    public ApartmentDetailVo getApartmentVoById(Long id) {

        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();

        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);

        BeanUtils.copyProperties(apartmentInfo,apartmentDetailVo);

        //图片
        LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();

        graphQueryWrapper.eq(GraphInfo::getItemType, APARTMENT);

        graphQueryWrapper.eq(GraphInfo::getItemId,id);

        List<GraphInfo> list = graphInfoService.list(graphQueryWrapper);

        List<GraphVo> graphVoList = new ArrayList<>();

        for (GraphInfo graphInfo : list) {
            GraphVo graphVo = GraphVo.builder().build();
            graphVo.setUrl(graphInfo.getUrl());
            graphVo.setName(graphInfo.getName());
            graphVoList.add(graphVo);
        }

        apartmentDetailVo.setGraphVoList(graphVoList);

        //标签列表
        LambdaQueryWrapper<ApartmentLabel> labelLambdaQueryWrapper = new LambdaQueryWrapper<>();

        labelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,id);

        List<ApartmentLabel> apartmentLabelList = apartmentLabelService.list(labelLambdaQueryWrapper);

        List<LabelInfo> labelInfoList = new ArrayList<>();

        for (ApartmentLabel apartmentLabel : apartmentLabelList) {

            LabelInfo labelInfo = labelInfoService.getById(apartmentLabel.getLabelId());

            labelInfoList.add(labelInfo);
        }

        apartmentDetailVo.setLabelInfoList(labelInfoList);

        //配套列表
        LambdaQueryWrapper<ApartmentFacility> facilityLambdaQueryWrapper = new LambdaQueryWrapper<>();

        facilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,id);

        List<ApartmentFacility> apartmentFacilityList = apartmentFacilityService.list(facilityLambdaQueryWrapper);

        List<FacilityInfo> facilityInfoList = new ArrayList<>();

        for (ApartmentFacility apartmentFacility : apartmentFacilityList) {

            FacilityInfo facilityInfo = facilityInfoService.getById(apartmentFacility.getFacilityId());

            facilityInfoList.add(facilityInfo);
        }

        apartmentDetailVo.setFacilityInfoList(facilityInfoList);

        //杂费列表
        List<FeeValueVo> feeValueVoList = apartmentFeeValueService.selectListFeeValueVo(id);

        apartmentDetailVo.setFeeValueVoList(feeValueVoList);

        return apartmentDetailVo;
    }

    @Override
    public void removeApartmentById(Long id) {
        //删除图片
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId, id);

        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, APARTMENT);

        graphInfoService.remove(graphInfoLambdaQueryWrapper);

        //删除标签
        LambdaQueryWrapper<ApartmentLabel> apartmentLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();

        apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,id );

        apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);

        //删除配套
        LambdaQueryWrapper<ApartmentFacility> facilityLambdaQueryWrapper = new LambdaQueryWrapper<>();

        facilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,id);

        apartmentFacilityService.remove(facilityLambdaQueryWrapper);

        //删除杂费
        LambdaQueryWrapper<ApartmentFeeValue> feeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();

        feeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId,id);

        apartmentFeeValueService.remove(feeValueLambdaQueryWrapper);

        //删除房间
        LambdaQueryWrapper<RoomInfo> roomInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        roomInfoLambdaQueryWrapper.eq(RoomInfo::getApartmentId,id);

        roomInfoMapper.delete(roomInfoLambdaQueryWrapper);

        apartmentInfoMapper.deleteById(id);


    }

    @Override
    public List<ApartmentInfo> getlistInfoByDistrictId(Long id) {

        LambdaQueryWrapper<ApartmentInfo> districtInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        districtInfoLambdaQueryWrapper.eq(ApartmentInfo::getDistrictId,id);

        return apartmentInfoMapper.selectList(districtInfoLambdaQueryWrapper);
    }
}




