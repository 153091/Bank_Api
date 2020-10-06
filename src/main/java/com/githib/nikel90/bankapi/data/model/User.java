package com.githib.nikel90.bankapi.data.model;

public class User {
    private long id;
    private String Surname;
    private String Name;
    private int Age;

//    Какой конструктор???? И нужно ли переопределять Equals, Hashcode


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        this.Surname = surname;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        this.Age = age;
    }
}
