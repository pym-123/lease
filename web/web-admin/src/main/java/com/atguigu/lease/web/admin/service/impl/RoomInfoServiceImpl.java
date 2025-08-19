package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.model.enums.ReleaseStatus;
import com.atguigu.lease.web.admin.mapper.*;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.attr.AttrValueVo;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.atguigu.lease.web.admin.vo.room.RoomDetailVo;
import com.atguigu.lease.web.admin.vo.room.RoomItemVo;
import com.atguigu.lease.web.admin.vo.room.RoomQueryVo;
import com.atguigu.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.atguigu.lease.model.enums.ItemType.ROOM;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {
    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private RoomAttrValueService roomAttrValueService;

    @Autowired
    private RoomFacilityService roomFacilityService;

    @Autowired
    private RoomLabelService roomLabelService;

    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;

    @Autowired
    private RoomLeaseTermService roomLeaseTermService;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Autowired
    private AttrValueService attrValueService;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private LeaseTermMapper leaseTermMapper;

    @Autowired
    private RoomAttrValueMapper roomAttrValueMapper;

    @Autowired
    private RoomFacilityMapper roomFacilityMapper;

    @Autowired
    private RoomLabelMapper roomLabelMapper;

    @Autowired
    private RoomLeaseTermMapper roomLeaseTermMapper;

    @Autowired
    private RoomPaymentTypeMapper roomPaymentTypeMapper;


    @Override
    public void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo) {

        super.saveOrUpdate(roomSubmitVo);

        if (roomSubmitVo.getId() != null) {
            //删除图片列表
            LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();

            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ROOM);
            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId, roomSubmitVo.getId());

            graphInfoService.remove(graphInfoLambdaQueryWrapper);

            //删除属性信息列表
            LambdaQueryWrapper<RoomAttrValue> roomAttrValueLambdaQueryWrapper = new LambdaQueryWrapper<>();

            roomAttrValueLambdaQueryWrapper.eq(RoomAttrValue::getRoomId, roomSubmitVo.getId());

            roomAttrValueService.remove(roomAttrValueLambdaQueryWrapper);

            //删除配套信息
            LambdaQueryWrapper<RoomFacility> roomFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();

            roomFacilityLambdaQueryWrapper.eq(RoomFacility::getRoomId, roomSubmitVo.getId());

            roomFacilityService.remove(roomFacilityLambdaQueryWrapper);

            //删除标签信息
            LambdaQueryWrapper<RoomLabel> roomLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();

            roomLabelLambdaQueryWrapper.eq(RoomLabel::getRoomId, roomSubmitVo.getId());

            roomLabelService.remove(roomLabelLambdaQueryWrapper);

            //删除支付方式信息
            LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();

            roomPaymentTypeLambdaQueryWrapper.eq(RoomPaymentType::getRoomId, roomSubmitVo.getId());

            roomPaymentTypeService.remove(roomPaymentTypeLambdaQueryWrapper);

            //删除可选租期列表
            LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermLambdaQueryWrapper = new LambdaQueryWrapper<>();

            roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId, roomSubmitVo.getId());

            roomLeaseTermService.remove(roomLeaseTermLambdaQueryWrapper);

        }
        //更新图片信息
        List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();

//        List<GraphInfo> graphInfoList = new ArrayList<>();
//
//        for (GraphVo graphVo : graphVoList) {
//
//            GraphInfo graphInfo = new GraphInfo();
//
//            graphInfo.setItemId(roomSubmitVo.getId());
//
//            graphInfo.setName(graphVo.getName());
//
//            graphInfo.setUrl(graphVo.getUrl());
//
//            graphInfo.setItemType(ROOM);
//
//            graphInfoList.add(graphInfo);
//        }
        List<GraphInfo> graphInfoList = new ArrayList<>();

        graphInfoList = graphVoList.stream().map(graphVo -> {
            GraphInfo graphInfo = new GraphInfo();
            graphInfo.setName(graphVo.getName());
            graphInfo.setUrl(graphVo.getUrl());
            graphInfo.setItemType(ROOM);
            graphInfo.setItemId(roomSubmitVo.getId());
            return graphInfo;
        }).toList();

        graphInfoService.saveBatch(graphInfoList);

        //更新属性列表信息
        List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();

        List<RoomAttrValue> roomAttrValueList = new ArrayList<>();

        roomAttrValueList = attrValueIds.stream().map(aLong -> {
            RoomAttrValue roomAttrValue = RoomAttrValue.builder().build();
            roomAttrValue.setRoomId(roomSubmitVo.getId());
            roomAttrValue.setAttrValueId(aLong);
            return roomAttrValue;
        }).toList();

        roomAttrValueService.saveBatch(roomAttrValueList);

        //更新配套列表信息
        List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();

        List<RoomFacility> roomFacilityList = new ArrayList<>();

        roomFacilityList = facilityInfoIds.stream().map(facilityInfoId -> {
            RoomFacility roomFacility = RoomFacility.builder().build();
            roomFacility.setRoomId(roomSubmitVo.getId());
            roomFacility.setFacilityId(facilityInfoId);
            return roomFacility;
        }).toList();

        roomFacilityService.saveBatch(roomFacilityList);

        //更新标签列表信息
        List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();

        List<RoomLabel> roomLabelList = new ArrayList<>();
        roomLabelList = labelInfoIds.stream().map(labelInfoId -> {
            RoomLabel roomLabel = RoomLabel.builder().build();
            roomLabel.setLabelId(labelInfoId);
            roomLabel.setRoomId(roomSubmitVo.getId());
            return roomLabel;
        }).toList();

        roomLabelService.saveBatch(roomLabelList);

        //更新支付方式
        List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();

        List<RoomPaymentType> roomPaymentTypeList = new ArrayList<>();

        roomPaymentTypeList = paymentTypeIds.stream().map(paymentTypeId->{
            RoomPaymentType roomPaymentType = RoomPaymentType.builder().build();
            roomPaymentType.setRoomId(roomSubmitVo.getId());
            roomPaymentType.setPaymentTypeId(paymentTypeId);
            return roomPaymentType;
        }).toList();

        roomPaymentTypeService.saveBatch(roomPaymentTypeList);

        //更新可选租期列表
        List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();

        List<RoomLeaseTerm> roomLeaseTermList = new ArrayList<>();

        roomLeaseTermList = leaseTermIds.stream().map(leaseTermId->{
            RoomLeaseTerm roomLeaseTerm = RoomLeaseTerm.builder().build();
            roomLeaseTerm.setLeaseTermId(leaseTermId);
            roomLeaseTerm.setRoomId(roomSubmitVo.getId());
            return roomLeaseTerm;
        }).toList();

        roomLeaseTermService.saveBatch(roomLeaseTermList);

    }

    @Override
    public IPage<RoomItemVo> iPageList(IPage<RoomItemVo> iPage, RoomQueryVo queryVo) {

        return roomInfoMapper.iPageList(iPage,queryVo);


    }

    @Override
    public RoomDetailVo getDetailById(Long id) {

        RoomDetailVo roomDetailVo = new RoomDetailVo();

        RoomInfo roomInfo = roomInfoMapper.selectById(id);

        BeanUtils.copyProperties(roomInfo,roomDetailVo);

        //获取公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoService.getById(roomInfo.getApartmentId());

        roomDetailVo.setApartmentInfo(apartmentInfo);

        //获取图片列表信息
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId, roomInfo.getId());
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType,ROOM);
        List<GraphInfo> graphInfoList = graphInfoService.list(graphInfoLambdaQueryWrapper);

        List<GraphVo> graphVoList = new ArrayList<>();

        graphVoList = graphInfoList.stream().map(graphInfo -> {
            GraphVo graphVo = new GraphVo();
            graphVo.setName(graphInfo.getName());
            graphVo.setUrl(graphInfo.getUrl());
            return graphVo;
        }).toList();

        roomDetailVo.setGraphVoList(graphVoList);

        //获取属性列表信息

        List<AttrValueVo> attrValueVoList = attrValueService.getAttrValueVoList(roomInfo.getId());

        roomDetailVo.setAttrValueVoList(attrValueVoList);

        //获取配套信息

        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectfacilityInfoList(roomInfo.getId());

        roomDetailVo.setFacilityInfoList(facilityInfoList);

        //获取标签信息表
        List<LabelInfo> labelInfoList = labelInfoMapper.selectLabelInfoList(roomInfo.getId());

        roomDetailVo.setLabelInfoList(labelInfoList);

        //获取支付方式信息
        List<PaymentType> paymentTypeList = paymentTypeMapper.selectPaymentTypeList(roomInfo.getId());

        roomDetailVo.setPaymentTypeList(paymentTypeList);

        //获取可选租期信息
        List<LeaseTerm> leaseTermList = leaseTermMapper.selectLeaseTermList(roomInfo.getId());

        roomDetailVo.setLeaseTermList(leaseTermList);

        return roomDetailVo;
    }

    @Override
    public void removeRoomById(Long id) {
        //删除图片
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ROOM);

        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId,id);

        graphInfoService.remove(graphInfoLambdaQueryWrapper);

        //删除房间属性
        LambdaQueryWrapper<RoomAttrValue> roomAttrValueLambdaQueryWrapper = new LambdaQueryWrapper<>();

        roomAttrValueLambdaQueryWrapper.eq(RoomAttrValue::getRoomId, id);

        roomAttrValueMapper.delete(roomAttrValueLambdaQueryWrapper);

        //删除房间配套
        LambdaQueryWrapper<RoomFacility> roomFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();

        roomFacilityLambdaQueryWrapper.eq(RoomFacility::getRoomId,id);

        roomFacilityMapper.delete(roomFacilityLambdaQueryWrapper);

        //删除房间标签
        LambdaQueryWrapper<RoomLabel> roomLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();

        roomLabelLambdaQueryWrapper.eq(RoomLabel::getRoomId,id);

        roomLabelMapper.delete(roomLabelLambdaQueryWrapper);

        //删除房间支付信息
        LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();

        roomPaymentTypeLambdaQueryWrapper.eq(RoomPaymentType::getRoomId,id);

        roomPaymentTypeMapper.delete(roomPaymentTypeLambdaQueryWrapper);

        //删除房间可选租期信息
        LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermLambdaQueryWrapper = new LambdaQueryWrapper<>();

        roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId,id);

        roomLeaseTermMapper.delete(roomLeaseTermLambdaQueryWrapper);

        //删除房间信息
        roomInfoMapper.deleteById(id);
    }

    @Override
    public void updateReleaseStatusById(Long id, ReleaseStatus status) {
        RoomInfo roomInfo = new RoomInfo();

        roomInfo.setId(id);

        roomInfo.setIsRelease(status);

        roomInfoMapper.updateById(roomInfo);
    }

    @Override
    public List<RoomInfo> listBasicByApartmentId(Long id) {

        LambdaQueryWrapper<RoomInfo> roomInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        roomInfoLambdaQueryWrapper.eq(RoomInfo::getApartmentId, id);

        return  roomInfoMapper.selectList(roomInfoLambdaQueryWrapper);

    }
}




