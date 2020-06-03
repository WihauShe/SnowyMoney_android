package cn.cslg.dao;

import cn.cslg.entity.User;

import java.util.List;

public interface IUserDao {
    User findUserById(String id);
    boolean insert(User user);
    boolean update(String id,String field,String value);

}
