package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.entity.MyFile;
import com.example.springboot.mapper.FileMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    /**
     * File upload interface: http://localhost:9090/file/{fileUuid}
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        File uploadParentFile = new File(fileUploadPath);
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }
        // Define a distinct uuid
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUuid);

        file.transferTo(uploadFile);

        String url = "http://localhost:9090/file/" + fileUuid;
        MyFile saveFile = new MyFile();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size);
        saveFile.setUrl(url);
        fileMapper.insert(saveFile);
        return url;
    }

    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response)
        throws IOException {
        File uploadFile = new File(fileUploadPath + fileUuid);

        ServletOutputStream os = response.getOutputStream();
        response.addHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUuid, "UTF-8"));
        response.setContentType("application/octet-stream");

        os.write(FileUtil.readBytes(uploadFile));



    }
}
