package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.BaseEntity;
import com.atguigu.lease.model.entity.SystemPost;
import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.web.admin.mapper.SystemPostMapper;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import com.atguigu.lease.web.admin.service.SystemUserService;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemPostMapper systemPostMapper;

    @Override
    public IPage<SystemUserItemVo> getSystemUserItemVoPage(IPage<SystemUserItemVo> iPage, SystemUserQueryVo queryVo) {
        return systemUserMapper.selectSystemUserItemVoPage(iPage,queryVo);
    }

    @Override
    public IPage<SystemUserItemVo> getSystemUserItemVoPage2(IPage<SystemUserItemVo> iPage, SystemUserQueryVo queryVo) {

        LambdaQueryWrapper<SystemUser> systemUserLambdaQueryWrapper = new LambdaQueryWrapper<>();

        systemUserLambdaQueryWrapper.like(queryVo.getName()!=null, SystemUser::getName,queryVo.getName());

        systemUserLambdaQueryWrapper.like(queryVo.getPhone()!=null, SystemUser::getPhone,queryVo.getPhone());

        List<SystemUser> systemUsers = systemUserMapper.selectList(systemUserLambdaQueryWrapper);

        List<SystemPost> systemPosts = systemPostMapper.selectList(null);

        Map<Long,String> map = systemPosts.stream().collect(Collectors.toMap(SystemPost::getId, SystemPost::getName));

        List<SystemUserItemVo> systemUserItemVoList = new ArrayList<>();

        for (SystemUser systemUser : systemUsers) {
            SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
            BeanUtils.copyProperties(systemUser,systemUserItemVo);
            systemUserItemVo.setPostName(map.get(systemUser.getPostId()));
            systemUserItemVoList.add(systemUserItemVo);
        }
        IPage<SystemUserItemVo> iPage1 = new Page<>(iPage.getCurrent(),iPage.getSize(),iPage.getTotal());
        iPage1.setRecords(systemUserItemVoList);

        return iPage1;
    }

    @Override
    public IPage<SystemUserItemVo> getSystemUserItemVoPage3(IPage<SystemUserItemVo> iPage, SystemUserQueryVo queryVo) {

        LambdaQueryWrapper<SystemUser> systemUserLambdaQueryWrapper = new LambdaQueryWrapper<>();

        systemUserLambdaQueryWrapper.like(queryVo.getName()!=null, SystemUser::getName,queryVo.getName());

        systemUserLambdaQueryWrapper.like(queryVo.getPhone()!=null, SystemUser::getPhone,queryVo.getPhone());

        List<SystemUser> systemUsers = systemUserMapper.selectList(systemUserLambdaQueryWrapper);

        List<SystemUserItemVo> systemUserItemVoList = new ArrayList<>();

        for (SystemUser systemUser : systemUsers) {
            SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
            BeanUtils.copyProperties(systemUser,systemUserItemVo);
            SystemPost systemPost = systemPostMapper.selectById(systemUser.getPostId());
            systemUserItemVo.setPostName(systemPost.getName());
            systemUserItemVoList.add(systemUserItemVo);
        }
        IPage<SystemUserItemVo> iPage1 = new Page<>(iPage.getCurrent(),iPage.getSize(),iPage.getTotal());
        iPage1.setRecords(systemUserItemVoList);
        return iPage1;
    }

    @Override
    public SystemUserItemVo getSystemUserItemVoById(Long id) {//循环100次耗时571
        return systemUserMapper.selectSystemUserItemVoById(id);
    }

    @Override
    public SystemUserItemVo getSystemUserItemVoById2(Long id) {//循环100次耗时840

        SystemUser systemUser = systemUserMapper.selectById(id);

        List<SystemPost> systemPosts = systemPostMapper.selectList(null);

        Map<Long,String> map = systemPosts.stream().collect(Collectors.toMap(BaseEntity::getId, SystemPost::getName));

        SystemUserItemVo systemUserItemVo  = new SystemUserItemVo();

        BeanUtils.copyProperties(systemUser,systemUserItemVo);

        systemUserItemVo.setPostName(map.get(systemUser.getPostId()));

        return systemUserItemVo;
    }



}




