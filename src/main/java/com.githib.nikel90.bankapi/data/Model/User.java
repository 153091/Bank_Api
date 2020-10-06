package com.githib.nikel90.bankapi.data.Model;

public class User {
    private int id;
    private String surname;
    private String name;
    private int age;

//    Какой конструктор???? И нужно ли переопределять Equals, Hashcode


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
