package com.ojc.service.impl;

import com.google.gson.Gson;
import com.ojc.domain.ResponseResult;
import com.ojc.enums.AppHttpCodeEnum;
import com.ojc.exception.SystemException;
import com.ojc.service.UploadService;
import com.ojc.utils.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


@Service
public class OssUploadService implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) {

        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();

        //对原始文件名进行判断
        if (! (originalFilename.endsWith(".png")||originalFilename.endsWith(".jpg")) ){
            throw new  SystemException(AppHttpCodeEnum.FILL_TYPE_ERROR);
        }

        //如果判断通过上传到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOSS(img,filePath);
        return ResponseResult.okResult(url);
    }

    private String uploadOSS(MultipartFile imgFile,String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "oJ2sEmoVaOz9fUPMpsFaASZPvlc7ZFxE3QRP30aY";
        String secretKey = "BL1_Yxxx36ZaizYmuQzDJf8JOE-ypzPi5os-d0_D";
        String bucket = "ojc-blog";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;

        try {
            /*byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);*/

            //上传数据
            /*
                从本地获取文件
                InputStream inputStream = Files.newInputStream(Paths.get("C:\\Users\\莉\\Desktop\\PhotoTest\\egg.png"));

            */
            //从网络中获取的文件,转为InputStream
            InputStream inputStream = imgFile.getInputStream();

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);

                //返回对应的外链cdn.hellokizunaai.xyz
                return "http://cdn.hellokizunaai.xyz/"+key;
                //return "http://rund6zhe5.hn-bkt.clouddn.com/" + key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

        return "333";

    }

}
