package com.smart.smart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@Api(tags = "文件相关")
public class FileUpLoadController {
    /**
     * 文件上传
     */
    @ApiOperation("单个文件上传")
    @RequestMapping("/fileUpLoad")
    @ResponseBody
    public String fileUpLoad(@RequestParam("fileName")MultipartFile file){
        if (file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName+"->" +size);
        String path = System.getProperty("user.dir");
        File dest = new File(path +"/files/" + fileName);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try{
            file.transferTo(dest);
            return "true";
        }catch (IllegalStateException e){
            e.printStackTrace();
            return "false";
        }catch (IOException e){
            e.printStackTrace();
            return "false";
        }
    }
    /**
     * 多文件上传
     */
    @ApiOperation("多个文件上传")
    @RequestMapping("/multifileUpLoad")
    public String multifileUpLoad(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName");
        if (files.isEmpty()){
            return "false";
        }
        String path = System.getProperty("user.dir");

        for (MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName+"->"+size);
            if(file.isEmpty()){
                return "false";
            }else {
                File dest = new File(path + "/files/"+fileName);
                if (!dest.getParentFile().exists()){
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return "false";
                }
            }
        }
        return "true";
    }

    /**
     * 文件下载
     * @param response
     * @param fileName
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation("文件下载")
    @RequestMapping("/download")
    public String donload(HttpServletResponse response,@RequestParam("fileName") String fileName,final HttpServletRequest request) throws UnsupportedEncodingException{
        String path = System.getProperty("user.dir");
        File dest = new File(path +"/files/" + fileName);
        if ((dest.exists())){
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition","attachment;fileName="+java.net.URLEncoder.encode(fileName,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                fileInputStream = new FileInputStream(dest);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                int i = bufferedInputStream.read(buffer);
                while (i != -1){
                    os.write(buffer);
                    i = bufferedInputStream.read(buffer);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("---file download--"+fileName);
            try
            {
                bufferedInputStream.close();
                fileInputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
