package cn.cslg.snowymoney.entity;

public class Account {
    private String userId;
    private String userPass;
    private int userBalance;
    private int userCredit;

    public Account() {
    }

    public Account(String userId, String userPass, int userBalance, int userCredit) {
        this.userId = userId;
        this.userPass = userPass;
        this.userBalance = userBalance;
        this.userCredit = userCredit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public int getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(int userBalance) {
        this.userBalance = userBalance;
    }

    public int getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(int userCredit) {
        this.userCredit = userCredit;
    }
}
