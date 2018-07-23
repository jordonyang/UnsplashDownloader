package com.jordon.unsplash_downloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.jordon")
public class UnsplashDownloaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnsplashDownloaderApplication.class, args);
    }
}
