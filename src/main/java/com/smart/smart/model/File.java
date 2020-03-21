package com.smart.smart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@ApiModel("文件实体")
@Entity
@Table(name = "file")
public class File {
    @ApiModelProperty(name = "文件id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    @ApiModelProperty(name = "文件类型")
    String fileType;
    @ApiModelProperty(name = "文件名")
    String fileName;
    @ApiModelProperty(name = "文件简介")
    String fileIntro;
    @ApiModelProperty(name = "上传者id")
    String userId;
    @ApiModelProperty(name = "积分")
    int point;
    @ApiModelProperty(name = "上传时间")
    @Column(name = "CREATE_TIME",insertable = false,updatable = false)
    @Generated(GenerationTime.INSERT)
    Timestamp createTime;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @ApiModelProperty(name = "点赞数")
    int likes;
    @ApiModelProperty(name = "下载人数")
    int downNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileIntro() {
        return fileIntro;
    }

    public void setFileIntro(String fileIntro) {
        this.fileIntro = fileIntro;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDownNum() {
        return downNum;
    }

    public void setDownNum(int downNum) {
        this.downNum = downNum;
    }
}
