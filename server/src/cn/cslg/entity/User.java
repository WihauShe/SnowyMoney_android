package cn.cslg.entity;

public class User {
    private String id;
    private String img;
    private String name;
    private String sex;
    private int age;
    private String school;

    public User() {
    }

    public User(String id, String img, String name, String sex, int age, String school) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.school = school;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
