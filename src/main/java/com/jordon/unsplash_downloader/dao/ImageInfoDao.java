package com.jordon.unsplash_downloader.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jordon.unsplash_downloader.po.ImageInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图片信息mapper
 * @author Jordon
 * @since 2018-07-23
 */
@Mapper
public interface ImageInfoDao extends BaseMapper<ImageInfo> {
}
