package cn.cslg.entity;

public class Account {
    private String id;
    private String password;
    private int balance;
    private int credit;

    public Account() {
    }
    public Account(String id, String password){
        this.id = id;
        this.password = password;
    }
    public Account(String id, String password, int balance, int credit) {
        this.id = id;
        this.password = password;
        this.balance = balance;
        this.credit = credit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
