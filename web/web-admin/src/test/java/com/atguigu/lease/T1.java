package com.atguigu.lease;

import com.atguigu.lease.common.minio.MinioProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class T1 {
    @Autowired
    private MinioProperties minioProperties;
    @Test
    public void test1(){
        String bucketName = minioProperties.getBucketName();
        System.out.println("bucketName = " + bucketName);

    }

}
