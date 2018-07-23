package com.jordon.unsplash_downloader.vo;

import com.jordon.unsplash_downloader.po.ImageUrl;
import com.jordon.unsplash_downloader.po.User;
import lombok.Data;

@Data
public class ImageInfoVo {
    private String id;
    private String created_at;
    private Integer width;
    private Integer height;
    private String color;
    private String description;
    private Integer likes;
    private ImageUrl urls;
    private User user;
}
