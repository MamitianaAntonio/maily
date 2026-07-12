package com.antonio.demo.file;

import com.antonio.demo.PojaGenerated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@PojaGenerated
@Configuration
public class S3Conf {

  @Bean
  public S3Client getS3Client(@Value("eu-west-3") String region) {
    return S3Client.builder()
        .region(Region.of(region))
        .build();
  }
}
