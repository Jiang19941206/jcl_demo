package com.jiangcl.alipay.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.temporal.TemporalAdjusters;

/**
 * @author jiangcl
 * @date 2020/9/4
 * @desc
 */
@Controller
public class FileController {

    @RequestMapping("index")
    public String index(){
        System.out.println("即将跳转首页");
        return "index";
    }

    @RequestMapping("/file/upload")
    @ResponseBody
    public void upload(@RequestParam("fileName")MultipartFile multipartFile,
                       HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        
        long size = multipartFile.getSize();
        System.out.println("文件的大小是：" + size);

        double offset = 0.0;

        InputStream in = null;
        OutputStream out = null;
        try {
            in = multipartFile.getInputStream();
            String filename = multipartFile.getOriginalFilename();
            out = new FileOutputStream("E:\\springboot\\fileUpload\\" + filename);

            int i;
            byte[] bytes = new byte[2048];
            System.out.println("开始上传时间：" + System.currentTimeMillis());
            while ((i = in.read(bytes)) != -1){
                offset = offset + i;
                session.setAttribute(sessionId,offset/size);
                out.write(bytes,0,i);
            }
            System.out.println("上传结束时间：" + System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null){
                    in.close();
                }if (out != null){
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/file/progress")
    @ResponseBody
    public Double fileProgress(HttpServletRequest request){
        HttpSession session = request.getSession();
        Double data = (Double)session.getAttribute(session.getId());
        return data;
    }
}
