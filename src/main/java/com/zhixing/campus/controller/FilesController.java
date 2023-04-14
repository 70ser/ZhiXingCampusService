package com.zhixing.campus.controller;

import cn.hutool.core.date.DateUtil;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.zhixing.campus.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@CrossOrigin
@RestController
@RequestMapping("/files")
public class FilesController {
    //body 是form-data key= file value=文件
    @PutMapping(consumes = "multipart/form-data")
    public Result upload(MultipartFile file) {
        try{
            String filename = file.getOriginalFilename();
            String parts= filename.substring(filename.lastIndexOf("."));//带.
            if(parts.length() == 0){
                return Result.error();
            }
            String  timeStamp = String.valueOf(DateUtil.current());
            filename = timeStamp + parts;
            BasicAWSCredentials credentials = new BasicAWSCredentials(
                    "09aba217bdc2f4c2a27f2bf504bbfbdc",
                    "fd37ba61ab6015a4a58be98dccddf07e391ac6b9a5d0a3129c37786083481189");
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(
                                    "https://0ce8c6bc72fff37eb614635de9908c5e.r2.cloudflarestorage.com/zhixingcampus",
                                    "apac"))
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();
            String bucketName = "zhixingcampus";
            ObjectMetadata metadata = new ObjectMetadata();
            // Set its content length to the size of the byte array
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            s3Client.putObject(bucketName, filename, file.getInputStream(), metadata);
            return Result.success("https://files.zhix.tk/"+filename);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error();
        }

    }
    //body 是二进制文件，后缀从content-type中计算，部分后缀会出错
    @PutMapping()
    public Result upload(HttpServletRequest request) {
        try{
            String contentType = request.getContentType();
            String  timeStamp = String.valueOf(DateUtil.current());
            BasicAWSCredentials credentials = new BasicAWSCredentials(
                    "09aba217bdc2f4c2a27f2bf504bbfbdc",
                    "fd37ba61ab6015a4a58be98dccddf07e391ac6b9a5d0a3129c37786083481189");
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(
                                    "https://0ce8c6bc72fff37eb614635de9908c5e.r2.cloudflarestorage.com/zhixingcampus",
                                    "apac"))
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();
            String bucketName = "zhixingcampus";
            ObjectMetadata metadata = new ObjectMetadata();
            //metadata.setContentLength(request.getContentLengthLong());
            //算了缓存就缓存吧
            //getContentLengthLong() 只是返回请求头中的Content-Length，不是实际的文件大小
            metadata.setContentType(request.getContentType());
            InputStream inputStream = request.getInputStream();
            //String type = FileTypeUtil.getType(inputStream);
            String type = contentType.substring(contentType.lastIndexOf("/")+1);//不带.
            String name = timeStamp + "." + type;
            s3Client.putObject(bucketName, name, inputStream, metadata);
            return Result.success("https://files.zhix.tk/"+name);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}
