package com.jordon.unsplash_downloader.controller;

import com.jordon.unsplash_downloader.service.ImageInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/image")
public class ImageInfoController {

    @Resource
    private ImageInfoService imageInfoService;
    private static final String TARGET_URL = "https://unsplash.com/napi/photos";

    @RequestMapping("/download")
    public void download(@RequestParam(value = "page",defaultValue = "1")String page,
                         @RequestParam(value = "perPage",defaultValue = "30")String perPage){
        imageInfoService.getData(TARGET_URL, page,perPage);
    }
}
