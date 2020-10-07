package com.githib.nikel90.bankapi.data.model;

public class User {
    private long id;
    private String surname;
    private String name;
    private int age;
    private String login;
    private String password;

    public User() {
    }

    public User(String surname, String name, int age, String login, String password) {
        this.surname = surname;
        this.name = name;
        this.age = age;
        this.login = login;
        this.password = password;
    }

    public User(long id, String surname, String name, int age, String login, String password) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.age = age;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
