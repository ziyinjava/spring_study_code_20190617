package com.itheima.dbassit;

import com.itheima.handler.ResultSetHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 封装的工具类
 */
public class DBAssit {

    private DataSource dataSource;

    public DBAssit(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 执行增删改的方法
     * @param sql
     * @param params
     * @return
     */
    public int update(String sql,Object... params){
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            //1.得到连接
            conn = dataSource.getConnection();
            //2.使用连接和参数的sql语句创建预处理对象
            pstm = conn.prepareStatement(sql);
            //3.得到sql语句参数的源信息（有几个参数，都什么类型等等）
            ParameterMetaData pmd = pstm.getParameterMetaData();
            //4.判断语句中参数的个数和方法参数params的个数是否一致，不一致肯定没法执行
            int parameterCount = pmd.getParameterCount();//参数的个数（问号的个数)
            if(parameterCount > 0) {
                if (params == null) {
                    throw new NullPointerException("没有sql语句执行必须的参数");
                }
                if (params.length != parameterCount) {
                    throw new RuntimeException("传入的参数个数和语句所需的参数个数不一致，语句无法执行");
                }
            }
            //5.给sql语句的参数赋值
            for(int i=0;i<parameterCount;i++){
                pstm.setObject(i+1,params[i]);
            }
            //6.执行语句
            int res = pstm.executeUpdate();
            //7.返回执行结果
            return res;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            release(conn,pstm,null);
        }
    }

    public  Object query(String sql, ResultSetHandler rsh, Object... params){
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //1.得到连接
            conn = dataSource.getConnection();
            //2.使用连接和参数的sql语句创建预处理对象
            pstm = conn.prepareStatement(sql);
            //3.得到sql语句参数的源信息（有几个参数，都什么类型等等）
            ParameterMetaData pmd = pstm.getParameterMetaData();
            //4.判断语句中参数的个数和方法参数params的个数是否一致，不一致肯定没法执行
            int parameterCount = pmd.getParameterCount();//参数的个数（问号的个数)
            if(parameterCount > 0) {
                if (params == null) {
                    throw new NullPointerException("没有sql语句执行必须的参数");
                }
                if (params.length != parameterCount) {
                    throw new RuntimeException("传入的参数个数和语句所需的参数个数不一致，语句无法执行");
                }
            }
            //5.给sql语句的参数赋值
            for(int i=0;i<parameterCount;i++){
                pstm.setObject(i+1,params[i]);
            }
            //6.执行语句
            rs = pstm.executeQuery();
            //7.返回执行结果
            return rsh.handle(rs);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            release(conn,pstm,rs);
        }
    }


    private void release(Connection conn,PreparedStatement pstm,ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(pstm != null){
            try {
                pstm.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(conn != null){
            try {
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
