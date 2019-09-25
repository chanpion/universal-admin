package com.chanpion.admin.system.service;

/**
 * @author April Chen
 * @date 2019/9/25 20:25
 */
public interface BaseService<T> {

    T findOne(Long id);

    void update(T t);


}
