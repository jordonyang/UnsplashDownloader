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

    /**
     * 分页下载
     * @param page  第几页
     * @param perPage   每页条数
     * @param level   图片大小
     */
    @RequestMapping("/download")
    public void download(@RequestParam(value = "page", defaultValue = "1") String page,
                         @RequestParam(value = "perPage", defaultValue = "30") String perPage,
                         @RequestParam(value = "level", defaultValue = "4") Integer level){
        imageInfoService.getData(TARGET_URL, page, perPage, level);
    }

    /**
     * 刷新缓存
     */
    @RequestMapping("/flushDB")
    public void flushDB(){
        imageInfoService.flushDB();
    }
}
