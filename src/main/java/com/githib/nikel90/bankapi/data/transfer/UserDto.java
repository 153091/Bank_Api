package com.githib.nikel90.bankapi.data.transfer;

public class UserDto {
    private final long id;
    private final String surname;
    private final String name;
    private final int age;
    private String login;


    public UserDto(long id, String surname, String name, int age, String login) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.age = age;
        this.login = login;
    }
}
