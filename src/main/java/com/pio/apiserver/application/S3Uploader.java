package com.pio.apiserver.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * S3에 파일을 업로드 해주는 서비스입니다.
 */
@Component
public class S3Uploader {
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3Uploader(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /**
     * 주어진 파일을 S3에 업로드하고 url을 리턴합니다.
     *
     * @param fileStream  업로드할 파일
     * @param contentType 업로드할 파일의 타입
     * @return S3 url
     */
    public String upload(byte[] fileStream, String contentType) {
        String fileName = UUID.randomUUID().toString();
        InputStream inputStream = new ByteArrayInputStream(fileStream);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(fileStream.length);

        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, inputStream, metadata));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
