package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.context.LoginUser;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.utils.JwtUtil;
import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.model.enums.BaseEnum;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import com.atguigu.lease.web.admin.service.LoginService;
import com.atguigu.lease.web.admin.vo.login.CaptchaVo;
import com.atguigu.lease.web.admin.vo.login.LoginVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.db.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public CaptchaVo getCaptcha() {

        SpecCaptcha specCaptcha = new SpecCaptcha(130,50,4);

        String img = specCaptcha.toBase64();

        String text = specCaptcha.text().toLowerCase();

        String key = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(key,text,60, TimeUnit.SECONDS);

        return new CaptchaVo(img, key);
    }

    @Override
    public String login(LoginVo loginVo,String remoteAddr) {

        String captchaKey = loginVo.getCaptchaKey();
        String username = loginVo.getUsername();
        String captchaCode = loginVo.getCaptchaCode();
        String password = loginVo.getPassword();

        String value = redisTemplate.opsForValue().get(captchaKey);
        Assert.hasText(value,ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED.getMessage());

        Assert.isTrue(captchaCode.equalsIgnoreCase(value), ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR.getMessage());

        LambdaQueryWrapper<SystemUser> systemUserLambdaQueryWrapper = new LambdaQueryWrapper<>();

        systemUserLambdaQueryWrapper.eq(SystemUser::getUsername, username);

        SystemUser systemUser = systemUserMapper.selectOne(systemUserLambdaQueryWrapper);

        Assert.hasText(systemUser.getUsername(), ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR.getMessage());

        Assert.isTrue(systemUser.getPassword().equalsIgnoreCase(DigestUtils.md5Hex(password)), ResultCodeEnum.ADMIN_ACCOUNT_ERROR.getMessage());

        Assert.isTrue(systemUser.getStatus().getName().equals(BaseStatus.ENABLE.getName()), ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR.getMessage());

        LoginUser loginUser = new LoginUser(systemUser.getId(), systemUser.getUsername());

        return JwtUtil.getToken(loginUser, remoteAddr);
    }

    @Override
    public SystemUserInfoVo getSystemUserInfoById(Long id) {

        SystemUser systemUser = systemUserMapper.selectById(id);

        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();

        systemUserInfoVo.setName(systemUser.getName());

        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());

        return systemUserInfoVo;
    }
}
