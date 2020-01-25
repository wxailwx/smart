package com.smart.smart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ApiModel("用户实体")
@Entity
public class User {

    @ApiModelProperty("用户姓名")
    private String name;
    @ApiModelProperty("学院")
    private String college;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("学号 id")
    @Id
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
