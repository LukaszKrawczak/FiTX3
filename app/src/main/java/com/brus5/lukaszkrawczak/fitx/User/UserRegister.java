package com.brus5.lukaszkrawczak.fitx.User;

/**
 * Created by lukaszkrawczak on 16.04.2018.
 */

public class UserRegister {

    private String username;
    private String name;
    private String password;
    private String email;
    private String sex;

    public UserRegister(String username, String name, String password, String email, String sex) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
