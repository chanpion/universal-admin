package com.chanpion.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author April Chen
 * @date 2019/5/30 19:08
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 3632181665115916990L;

    private Long id;
    private String username;
    private String password;
    private String salt;
    private short status;
    private String mobile;
    private Integer sex;
    private Integer age;
    private Date createTime;
    private Date updateTime;
}
