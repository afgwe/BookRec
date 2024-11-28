package com.promise.controller;

import lombok.Value;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
/*

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {
    // 典型的一个下载接口。
    @PostMapping(value = "/epub")
    public void downloadEpub(String chapter) throws Exception {
        Resource resource=  new ClassPathResource(chapter);

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);

        try (InputStream inputStream = resource.getInputStream()) {
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

*/
