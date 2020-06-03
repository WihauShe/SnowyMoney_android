package cn.cslg.snowymoney.entity;

public class User {
    private String userId;
    private String userImg;
    private String userName;
    private String userSex;
    private int userAge;
    private String userSchool;

    public User(){

    }

    public User(String userId, String userImg, String userName, String userSex, int userAge, String userSchool) {
        this.userId = userId;
        this.userImg = userImg;
        this.userName = userName;
        this.userAge = userAge;
        this.userSex = userSex;
        this.userSchool = userSchool;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }
}
