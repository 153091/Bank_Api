package com.github.nikel90.bankapi.data.transfer;

public class UserDto {
    private long id;
    private String surname;
    private String name;
    private int age;
    private String login;

    public UserDto(long id, String surname, String name, int age, String login) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.age = age;
        this.login = login;
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

}
