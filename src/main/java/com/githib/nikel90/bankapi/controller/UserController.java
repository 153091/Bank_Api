package com.githib.nikel90.bankapi.controller;

import com.githib.nikel90.bankapi.service.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class UserController implements HttpHandler {
    public static final String REGISTER_USER_PATH = "/user/register";
    public static final String GET_ALL_USERS_PATH = "/user";
    public static final String GET_USER_BY_ID_PATH = "/user/";
    public static final String DELETE_USER_BY_ID_PATH = "/user/";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        final String method = httpExchange.getRequestMethod().toLowerCase();
        if (method.equals("get")) {
            final String query = httpExchange.getRequestURI().getQuery();
            if (query != null) {
                final String[] params = query.split("=");
//                UserService
            }
        } else if(method.equals("post")) {

        } else {
//            todo:return exception
        }

        if (REGISTER_USER_PATH.equals(httpExchange.getRequestURI())) {

            if (!"post".equals(httpExchange.getRequestMethod().toLowerCase())) {
                // TODO: 07.10.2020 Writer json error to output
            } else {
//                httpExchange.get
//                userController.processRegisterUser();
            }
        }
        httpExchange.getRequestURI();
    }
}
