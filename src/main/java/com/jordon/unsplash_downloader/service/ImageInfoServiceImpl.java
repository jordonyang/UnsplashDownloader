package com.jordon.unsplash_downloader.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.jordon.unsplash_downloader.dao.ImageInfoDao;
import com.jordon.unsplash_downloader.po.ImageInfo;
import com.jordon.unsplash_downloader.vo.ImageInfoVo;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jordon
 * @since 2018-07-24
 */
@Service
public class ImageInfoServiceImpl extends ServiceImpl<ImageInfoDao, ImageInfo> implements ImageInfoService {

    private static final String SAVING_FOLDER = "E:\\\\wallPapers\\\\";
    private static final Map<String,String> headers = new HashMap<>();

    @Resource
    private ImageInfoDao imageInfoDao;
    @Resource
    private  RedisTemplate<Object,ImageInfo> redisTemplate;

    static {
        headers.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Content-Type", "text/*, application/xml, or application/xhtml+xml. Mimetype=application/json");
        headers.put("User-Agent", "  Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
    }


    /**
     * 清除缓存
     */
    @Override
    public void flushDB() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    /**
     * 使用URL类库下载图片
     * @param url   请求路径
     */
    private void download(String url)  {
        int len;
        byte[] data = new byte[2];
        try {
            InputStream is = new URL(url).openConnection().getInputStream();
            OutputStream os = new FileOutputStream(SAVING_FOLDER + processFolder(url) + ".jpg");
            while (!((len = is.read(data)) == -1))
                os.write(data, 0, len);
            os.flush(); //强制写出
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 根据日期划分文件夹
     * @param url   请求路径
     * @return  日期式文件夹/时间戳式图片名
     */
    private String processFolder(String url){
        String[] strings = url.split("-");
        Timestamp timestamp = new Timestamp(Long.parseLong(strings[1]));
        String[] dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp).split(" ");

        File file = new File(SAVING_FOLDER);
        if (file.exists()){
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File folder : files){
                    if (folder.getName().equals(dateStr[0])){
                        break;
                    }
                    new File(SAVING_FOLDER + dateStr[0]).mkdir();
                }
            }else {
                new File(SAVING_FOLDER + dateStr[0]).mkdir();
            }
        }
        return dateStr[0] + "\\" +strings[1];
    }


    /**
     * 分页获取壁纸
     * @param url  请求地址
     * @param page  第几页
     * @param perPage   每页数据
     */
    public void getData(String url, String page, String perPage, Integer type) {
        ListOperations<Object, ImageInfo> operations = redisTemplate.opsForList();
        Connection connection = Jsoup.connect(url).ignoreContentType(true);
        connection.headers(headers);
        connection.data("page",page);
        //每页最多获取30条数据
        connection.data("per_page", perPage);
        connection.data("order_by", "latest");

        try {
            Document doc = connection.get();
            List<ImageInfoVo> list =JSON.parseArray(doc.body().html(), ImageInfoVo.class);
            for (ImageInfoVo imageInfoVo : list) {
                //check cache
                if (redisTemplate.hasKey(imageInfoVo.getId()) && operations.size(imageInfoVo.getId()) > 0) {
                    continue;
                }

                ImageInfo imageInfo = new ImageInfo();
                BeanUtils.copyProperties(imageInfoVo, imageInfo);
                imageInfo.setFull(imageInfoVo.getUrls().getFull());
                imageInfo.setRaw(imageInfoVo.getUrls().getRaw());
                imageInfo.setRegular(imageInfoVo.getUrls().getRegular());
                imageInfo.setThumb(imageInfoVo.getUrls().getThumb());
                imageInfo.setSmall(imageInfoVo.getUrls().getSmall());

                imageInfoDao.insertAllColumn(imageInfo);
                System.out.println(imageInfoVo);

                switch (type){
                    case 0:
                        download(imageInfo.getFull());
                        break;
                    case 1:
                        download(imageInfo.getRaw());
                        break;
                    case 2:
                        download(imageInfo.getThumb());
                        break;
                    case 3:
                        download(imageInfo.getRegular());
                        break;
                    default:
                        download(imageInfo.getSmall());
                        break;
                }
                //add cache
                operations.rightPush(imageInfo.getId(), imageInfo);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
