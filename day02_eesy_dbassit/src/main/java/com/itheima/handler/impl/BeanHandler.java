package com.itheima.handler.impl;

import com.itheima.handler.ResultSetHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 结果集封装的具体实现类。
 * 此类实现的是把一个结果集rs的内容封装到一个指定的实体类对象中
 * 使用要求：
 *      实体类中的属性必须和表中的列名一致（sql语句查询出来的列名一致）
 * @param <T>
 */
public class BeanHandler implements ResultSetHandler {

    private Class domainClass;

    public BeanHandler(Class domainClass) {
        this.domainClass = domainClass;
    }

    /**
     * 把rs的内容封装到domainClass所表示的类中
     * @param rs
     * @return
     */
    @Override
    public Object handle(ResultSet rs) {
        try {
            //1.创建一个实体类对象
            Object bean = domainClass.newInstance();
            //2.判断是否有结果集
            if(rs.next()){
                //3.得到结果集rs中所有的列名
                //要想得到列名，得先得到结果集的源信息
                ResultSetMetaData rsmd = rs.getMetaData();
                //得到源信息之后，还要得到有多少列
                int columnCount = rsmd.getColumnCount();
                //遍历列数
                for(int i=1;i<=columnCount;i++){
                    //得到每列的名称
                    String columnName = rsmd.getColumnName(i);
                    //列名其实就是实体类的属性名称，于是就可以使用列名得到实体类中属性的描述器
                    PropertyDescriptor pd = new PropertyDescriptor(columnName,domainClass);//实体类中定义的私有类成员和它的get以及set方法
                    //获取属性的写入方法(set方法)
                    Method method = pd.getWriteMethod();
                    //获取当前列名所对应的值
                    Object columnValue = rs.getObject(columnName);
                    //通过执行写方法把得到的值给属性赋上
                    method.invoke(bean,columnValue);
                }
            }
            //4.返回
            return bean;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
