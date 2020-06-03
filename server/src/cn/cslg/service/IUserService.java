package cn.cslg.service;

import cn.cslg.entity.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    boolean registerUser(String id,String name,String sex,int age,String school,String password);
    String[] getLoginInfo(String id);
    Map<String,String> getUserInfo(String id);
    boolean updateUserInfo(String id,String field,String value);
    boolean raiseUserBalance(String id,int flag);
    boolean deductUserBalance(String id,int flag);
    void deductUserCredit(String id);
}
