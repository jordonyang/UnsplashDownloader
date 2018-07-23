package com.jordon.unsplash_downloader.po;

import lombok.Data;

@Data
public class ImageInfo {
    private String id;
    private String created_at;
    private String uploader_name;
    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;
}
