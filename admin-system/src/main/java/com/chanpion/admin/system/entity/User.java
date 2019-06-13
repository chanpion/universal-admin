package com.chanpion.admin.system.entity;

import lombok.Data;

/**
 * @author April Chen
 * @date 2019/5/30 19:08
 */
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String phone;
}
