package com.chanpion.admin.system.entity;

import lombok.Data;

import java.util.Date;

/**
 * 角色
 *
 * @author April Chen
 * @date 2019/9/11 16:08
 */
@Data
public class Role {
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
