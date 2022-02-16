package com.pio.apiserver.application;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("S3Uploader")
class S3UploaderTest {
    static final String ACCESS_KEY = "";
    static final String SECRET_KEY = "";
    static final String REGION = "";
    static final String BUCKET = "";


    S3Uploader s3Uploader;

    @BeforeEach
    void prepare() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        s3Uploader = new S3Uploader(client);
        s3Uploader.setBucket(BUCKET);
    }

    @Nested
    @DisplayName("upload 메소드")
    class Describe_upload {
        @Nested
        @DisplayName("파일이 주어진다면")
        class Context_with_file {
            static final byte[] FILE = {0x34, 0x43, 0x57};
            static final String CONTENT_TYPE = "image/jpeg";

            @Test
            @DisplayName("url을 리턴한다.")
            void it_returns_url() {
                assertThat(s3Uploader.upload(FILE, CONTENT_TYPE)).startsWith("https://" + BUCKET);
            }
        }
    }
}