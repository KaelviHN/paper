package cn.blog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/3
 **/
public interface UploadService {
    /**
     * 上传图片
     * @param img
     * @return
     */
    String uploadImg(MultipartFile img);
}
