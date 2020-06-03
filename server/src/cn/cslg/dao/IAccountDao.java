package cn.cslg.dao;

import cn.cslg.entity.Account;

public interface IAccountDao {
    boolean insert(Account account);
    Account getAccountById(String id);
    boolean updateBalance(String id,String param);
    boolean deductCredit(String id);
}
