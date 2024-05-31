package cn.blog.service.impl;

import cn.blog.enums.AppHttpCodeEnum;
import cn.blog.exception.SystemException;
import cn.blog.service.UploadService;
import cn.blog.utils.PathUtils;
import com.UpYun;
import com.upyun.UpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/3
 **/
@Service
public class UploadServiceImpl implements UploadService {
    @Value("${upyun.bucket-name}")
    private String bucketName;

    @Value("${upyun.username}")
    private String username;

    @Value("${upyun.password}")
    private String password;

    @Value("${upyun.prefix_url}")
    private String prefixUrl;

    @Value("${upyun.dir}")
    private String dir;

    /**
     * 上传图片到upyun
     * @param img
     * @return
     */
    @Override
    public String uploadImg(MultipartFile img) {
        // 获取源文件名
        String originalFilename = img.getOriginalFilename();
        // 获取文件大小
        long fileSize = img.getSize();
        // 判断文件大小是否超过10MB
        if (fileSize > 10 * 1024 * 1024) {
            // 抛出文件大小超过限制的异常
            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }
        // 对原始文件名进行判断大小。只能上传png或jpg文件
        if (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadUpYun(img, filePath);
        return url;
    }

    private String uploadUpYun(MultipartFile img, String path) {
        // 创建upyun对象
        UpYun upYun = new UpYun(bucketName, username, password);
        // 将img对象转InputStream
        try {
            InputStream stream = img.getInputStream();
            upYun.writeFile(dir+ path, stream, true, null);
            return prefixUrl +dir+ path;
        } catch (IOException e) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        } catch (UpException e) {
            throw new SystemException(AppHttpCodeEnum.FILE_UPLOAD_ERROR);
        }
    }
}
