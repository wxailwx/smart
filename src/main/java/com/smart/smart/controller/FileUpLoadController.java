package com.smart.smart.controller;

import com.smart.smart.service.FileRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
@Api(tags = "文件相关")
public class FileUpLoadController {
    @Autowired
    private FileRepository repository;
    /**
     * 文件上传
     */
    @ApiOperation("单个文件上传")
    @PostMapping("/fileUpLoad")
    @ResponseBody
    public String fileUpLoad(
            @RequestParam("file") MultipartFile file
            ){
        if (file.isEmpty()){
            return "false";
        }
        com.smart.smart.model.File file1 = new com.smart.smart.model.File();
        int point = 0;
        String fileIntro = "";
        String  userId = "1";
        file1.setPoint(point);
        file1.setFileIntro(fileIntro);
        String fileName = file.getOriginalFilename();
        int begin = fileName.lastIndexOf(".") ;
        int last  = fileName.length();
        file1.setFileName(fileName);
        file1.setUserId(userId);
        file1.setFileType(fileName.substring(begin,last));
        file1=repository.saveAndFlush(file1);
        int size = (int) file.getSize();
        System.out.println(fileName+"->" +size);
        String path = System.getProperty("user.dir");
        File dest = new File(path +"/files/" + file1.getId()+"."+file1.getFileType());
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
    @PostMapping("/multifileUpLoad")
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
     * @param fileId
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation("文件下载")
    @GetMapping("/download")
    public String download(HttpServletResponse response,@RequestParam("fileId") int fileId,final HttpServletRequest request) throws UnsupportedEncodingException{
        String path = System.getProperty("user.dir");
        com.smart.smart.model.File file = repository.findById(fileId);
        File dest = new File(path +"/files/" + fileId+"."+file.getFileType());
        if (file!=null&&(dest.exists())){
            file.setDownNum(file.getDownNum()+1);
            repository.saveAndFlush(file);
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String fileName = file.getFileName();
            response.setHeader("Content-Disposition","attachment;fileName="+java.net.URLEncoder.encode(fileName,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            OutputStream os = null;
            if (file.getFileType()=="pdf")
                response.setContentType("application/pdf");
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
    @ApiOperation("获取所有文件列表")
    @GetMapping("/getAllFileList")
    /**
     * @Param pageNum
     * @Param size
     * @return List<File>
     */
    public List<com.smart.smart.model.File> getFileList(@RequestParam("pageNum") Integer pageNum,@RequestParam("size")Integer size){
        if (pageNum ==null)
            pageNum = 0;
        if (size == null)
            size = 10;
        PageRequest pageRequest = PageRequest.of(pageNum,size);
        Pageable pageable = pageRequest;
        Page<com.smart.smart.model.File> list = repository.findAll(pageable);
        return list.getContent();
    }
    @ApiOperation("模糊搜索文件名")
    @GetMapping("/getFileByName")
    public List<com.smart.smart.model.File> getFileByName(
            @RequestParam("pageNum")Integer pageNum,
            @RequestParam("size")Integer size,
            @RequestParam("fileName")String name){
        if (pageNum ==null)
            pageNum = 0;
        if (size == null)
            size = 10;
            Page<com.smart.smart.model.File> list = repository.findByFileNameContaining(name,PageRequest.of(pageNum,size));
            return list.getContent();
    }
}
