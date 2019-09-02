package com.chanpion.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;


/**
 * <p>
 * 部门表
 * </p>
 *
 * @author GaoJun.Zhou
 * @since 2017-03-06
 */
@TableName("sys_dept")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 描述
     */
    private String deptDesc;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
