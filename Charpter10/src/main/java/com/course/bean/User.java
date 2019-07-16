package com.course.bean;

import lombok.Data;

@Data
public class User {
    private String name;
    private String age;
    private String sex;
    private String adminName;
    private String adminPassword;

    public User() {

    }

    public User(String name, String age, String sex, String adminName, String adminPassword) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }
}
