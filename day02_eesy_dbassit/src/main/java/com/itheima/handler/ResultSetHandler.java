package com.itheima.handler;

import java.sql.ResultSet;

/**
 * 结果集封装的接口
 */
public interface ResultSetHandler {

    /**
     * 结果集封装的方法
     * @param rs
     * @return
     */
    Object handle(ResultSet rs);
}
