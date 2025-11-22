package com.mingle;

import com.mingle.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class MingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MingleApplication.class, args);
    }

}
