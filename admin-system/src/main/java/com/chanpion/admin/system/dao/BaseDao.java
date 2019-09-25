package com.chanpion.admin.system.dao;

import java.util.List;

/**
 * @author April Chen
 * @date 2019/9/6 15:58
 */
public interface BaseDao<T> {

    /**
     * 插入一条数据
     *
     * @param item
     * @return 成功条数
     */
    Integer insert(T item);

    /**
     * 更新记录
     *
     * @param item
     * @return
     */
    Integer update(T item);

    /**
     * 查询一条数据
     *
     * @param item
     * @return
     */
    T findOne(T item);

    /**
     *
     * @param item
     * @return
     */
    List<T> find(T item);
}
