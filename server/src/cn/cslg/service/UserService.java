package cn.cslg.service;

import cn.cslg.dao.AccountDao;
import cn.cslg.dao.IAccountDao;
import cn.cslg.dao.IUserDao;
import cn.cslg.dao.UserDao;
import cn.cslg.entity.Account;
import cn.cslg.entity.User;
import cn.cslg.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService implements IUserService {
    @Override
    public boolean registerUser(String id, String name, String sex, int age, String school, String password) {
        User user = new User(id,"",name,sex,age,school);
        Account account = new Account(id,password);
        IUserDao userDao = new UserDao();
        IAccountDao accountDao = new AccountDao();
        return userDao.insert(user) && accountDao.insert(account);
    }

    @Override
    public String[] getLoginInfo(String id) {
        IUserDao userDao = new UserDao();
        User user = userDao.findUserById(id);
        IAccountDao accountDao = new AccountDao();
        Account account = accountDao.getAccountById(id);
        if(user != null && account != null) {
            String[] logInfo = {user.getImg(),user.getName(),account.getPassword(),String.valueOf(account.getBalance())};
            return logInfo;
        }
        return null;
    }

    @Override
    public Map<String,String> getUserInfo(String id) {
        Map<String,String> map = new HashMap<>();
        IUserDao userDao = new UserDao();
        User user = userDao.findUserById(id);
        IAccountDao accountDao = new AccountDao();
        Account account = accountDao.getAccountById(id);
        if(user != null && account != null){
            map.put("id",user.getId());
            map.put("img",user.getImg());
            map.put("name",user.getName());
            map.put("sex",user.getSex());
            map.put("age",String.valueOf(user.getAge()));
            map.put("school",user.getSchool());
            map.put("credit",String.valueOf(account.getCredit()));
        }
        return map;
    }

    @Override
    public boolean updateUserInfo(String id, String field, String value) {
        IUserDao userDao = new UserDao();
        return userDao.update(id, field, value);
    }

    @Override
    public boolean raiseUserBalance(String id, int flag) {
        IAccountDao accountDao = new AccountDao();
        String param = "+ "+flag;
        return accountDao.updateBalance(id,param);
    }

    @Override
    public boolean deductUserBalance(String id, int flag) {
        IAccountDao accountDao = new AccountDao();
        String param = "- "+flag;
        return accountDao.updateBalance(id,param);
    }

    @Override
    public void deductUserCredit(String id) {
        IAccountDao accountDao = new AccountDao();
        accountDao.deductCredit(id);
    }
}
