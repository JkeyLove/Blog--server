package com.ojc.controller;



import com.ojc.domain.ResponseResult;
import com.ojc.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@RestController
public class UploadController {
    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult upLoadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }

}
