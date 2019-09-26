package com.chanpion.admin.system.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author April Chen
 * @date 2019/9/24 11:07
 */
@Data
public class Permission {
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String desc;
    private short status;
    private Date createTime;
    private Date updateTime;
}
