package com.jordon.unsplash_downloader.service;

import com.baomidou.mybatisplus.service.IService;
import com.jordon.unsplash_downloader.po.ImageInfo;

public interface ImageInfoService extends IService<ImageInfo> {
    void getData(String url, String page, String perPage, Integer type);

    void flushDB();
}
