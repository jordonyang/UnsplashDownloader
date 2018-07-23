package com.jordon.unsplash_downloader.po;

import lombok.Data;

@Data
public class ImageUrl {
    private Integer id;
    private String image_id;
    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;
}
