package com.atguigu.lease;

import com.atguigu.lease.common.minio.MinioProperties;
import com.atguigu.lease.web.admin.service.SystemUserService;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class T1 {
    @Autowired
    private MinioProperties minioProperties;
    @Autowired
    private SystemUserService systemUserService;
    @Test
    public void test1(){
        String bucketName = minioProperties.getBucketName();
        System.out.println("bucketName = " + bucketName);

    }
    @Test
    public void te2(){

        long l = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            SystemUserItemVo systemUserItemVoById = systemUserService.getSystemUserItemVoById(1L);
            System.out.println("systemUserItemVoById = " + systemUserItemVoById);
        }
        long l1 = System.currentTimeMillis();

        System.out.println("耗时："+ (l1-l));
    }

    @Test
    public void te3(){

        long l = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            SystemUserItemVo systemUserItemVoById = systemUserService.getSystemUserItemVoById2(1L);
            System.out.println("systemUserItemVoById = " + systemUserItemVoById);
        }
        long l1 = System.currentTimeMillis();

        System.out.println("耗时："+ (l1-l));
    }
    @Test
    public void te4(){//100:耗时:1259,1000:耗时:6663,10000:耗时:41315
        long l = System.currentTimeMillis();
        IPage<SystemUserItemVo> systemUserItemVoIPage = new Page<>(1,10);
        SystemUserQueryVo systemUserQueryVo = new SystemUserQueryVo();
//        systemUserQueryVo.setName("e");

        for (int i = 0; i < 10000; i++) {
            IPage<SystemUserItemVo> systemUserItemVoPage = systemUserService.getSystemUserItemVoPage(systemUserItemVoIPage, systemUserQueryVo);
            System.out.println("systemUserItemVoPage = " + systemUserItemVoPage);
        }
        long l1 = System.currentTimeMillis();
        System.out.println("耗时:"+(l1-l));
    }

    @Test
    public void te5(){//100:耗时:965//1000:耗时:3731,10000:耗时:20387
        long l = System.currentTimeMillis();
        IPage<SystemUserItemVo> systemUserItemVoIPage = new Page<>(1,10);
        SystemUserQueryVo systemUserQueryVo = new SystemUserQueryVo();
//        systemUserQueryVo.setName("e");

        for (int i = 0; i < 100000; i++) {
            IPage<SystemUserItemVo> systemUserItemVoPage = systemUserService.getSystemUserItemVoPage2(systemUserItemVoIPage, systemUserQueryVo);
            System.out.println("systemUserItemVoPage = " + systemUserItemVoPage);
        }
        long l1 = System.currentTimeMillis();
        System.out.println("耗时:"+(l1-l));
    }

    @Test
    public void te6(){//100:耗时:1367//1000:耗时:5308,10000:耗时:33755
        long l = System.currentTimeMillis();
        IPage<SystemUserItemVo> systemUserItemVoIPage = new Page<>(1,10);
        SystemUserQueryVo systemUserQueryVo = new SystemUserQueryVo();
//        systemUserQueryVo.setName("e");

        for (int i = 0; i < 100000; i++) {
            IPage<SystemUserItemVo> systemUserItemVoPage = systemUserService.getSystemUserItemVoPage3(systemUserItemVoIPage, systemUserQueryVo);
            System.out.println("systemUserItemVoPage = " + systemUserItemVoPage);
        }
        long l1 = System.currentTimeMillis();
        System.out.println("耗时:"+(l1-l));
    }

}
