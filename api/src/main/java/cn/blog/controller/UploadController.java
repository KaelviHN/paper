package cn.blog.controller;


import cn.blog.model.ResponseResult;
import cn.blog.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author KaelvihN
 */
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img) {
        return ResponseResult.okResult(uploadService.uploadImg(img));
    }
}
