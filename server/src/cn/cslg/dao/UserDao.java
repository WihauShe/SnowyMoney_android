package cn.cslg.dao;

import cn.cslg.entity.User;
import cn.cslg.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDao implements IUserDao {
    @Override
    public User findUserById(String id) {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from user where id = ?";
        User user = null;
        try{
            user = qr.query(DruidUtil.getConn(),sql,new BeanHandler<>(User.class),id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean insert(User user) {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into user(id,name,sex,age,school) values(?,?,?,?,?)";
        Object[] params = {user.getId(),user.getName(),user.getSex(),user.getAge(),user.getSchool()};
        try {
            int flag = 0;
            flag = qr.update(DruidUtil.getConn(),sql,params);
            if(flag>0) return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(String id,String field, String value) {
        QueryRunner qr = new QueryRunner();
        String sql = "update user set "+field+" = ? where id = ?";
        Object[] params = {value,id};
        try{
            int flag = 0;
            flag = qr.update(DruidUtil.getConn(),sql,params);
            if(flag>0) return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
