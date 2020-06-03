package cn.cslg.dao;

import cn.cslg.entity.Account;
import cn.cslg.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class AccountDao implements IAccountDao {
    @Override
    public boolean insert(Account account) {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into account(id,password) values(?,?)";
        Object[] params = {account.getId(),account.getPassword()};
        try {
            int affected = 0;
            affected = qr.update(DruidUtil.getConn(),sql,params);
            if(affected>0) return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account getAccountById(String id) {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from account where id = ?";
        Account account = null;
        try {
            account = qr.query(DruidUtil.getConn(),sql,new BeanHandler<>(Account.class),id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public boolean updateBalance(String id, String param) {
        QueryRunner qr = new QueryRunner();
        String sql = "update account set balance = balance "+param+" where id = ?";
        try {
            int affected = 0;
            affected = qr.update(DruidUtil.getConn(),sql,id);
            if(affected>0) return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deductCredit(String id) {
        QueryRunner qr = new QueryRunner();
        String sql = "update account set credit = credit - 5 where id = ?";
        try {
            int affected = 0;
            affected = qr.update(DruidUtil.getConn(),sql,id);
            if(affected>0) return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
