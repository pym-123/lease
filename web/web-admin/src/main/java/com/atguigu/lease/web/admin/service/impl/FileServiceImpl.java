package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.minio.MinioConfiguration;
import com.atguigu.lease.common.minio.MinioProperties;
import com.atguigu.lease.web.admin.service.FileService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.joda.time.DateTime;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private MinioProperties minioProperties;
    @Autowired
    private MinioConfiguration minioConfiguration;


    @Override

    public String upload(MultipartFile file) {
        log.info(minioProperties.getEndpoint());
        MinioClient minioClient = minioConfiguration.getMinioClient();

        try{
            boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if(!b){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(minioProperties.getBucketName()).config(createBucketPolicyConfig(minioProperties.getBucketName())).build());
            }
            String uuid = UUID.randomUUID().toString().replace("-", "");

            String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

            String fileName = DateTime.now().toString("yyyy/MM/dd/")+uuid+"."+filenameExtension;

            minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucketName()).stream(file.getInputStream(), file.getSize(), -1).object(fileName).build());

            return minioProperties.getEndpoint()+"/"+minioProperties.getBucketName()+"/"+fileName;


        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }


    private String createBucketPolicyConfig(String bucketName) {

        return """
            {
              "Statement" : [ {
                "Action" : "s3:GetObject",
                "Effect" : "Allow",
                "Principal" : "*",
                "Resource" : "arn:aws:s3:::%s/*"
              } ],
              "Version" : "2012-10-17"
            }
            """.formatted(bucketName);
    }
}
