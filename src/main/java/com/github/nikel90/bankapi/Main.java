package com.github.nikel90.bankapi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Console;
import org.h2.tools.DeleteDbFiles;
import org.h2.tools.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class Main {

    static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:~/test";

    public static final String USER = "sa";
    public static final String PASS = "";

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, URISyntaxException {


        HttpServer server = HttpServer.create(new InetSocketAddress(10000), 0);
        server.createContext("/test", new MyHandler());
        server.createContext("/test2", new MyHandler());
        server.setExecutor(null); //creates a default executor
        server.start();

        bsh2();

        Console console = new Console();
        console.runTool(args);

    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("This is uri " + httpExchange.getRequestURI());
            System.out.println("Path " + httpExchange.getRequestURI().getPath());
            System.out.println("Rawpath " + httpExchange.getRequestURI().getRawPath());
            System.out.println("Query " + httpExchange.getRequestURI().getQuery());
            System.out.println("RawQuery " + httpExchange.getRequestURI().getRawQuery());
            System.out.println("Method " + httpExchange.getRequestMethod());
            String response = "This is the response\n";

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }



    static void bsh2() throws ClassNotFoundException  {
        DeleteDbFiles.execute("~", "test", true);

        Connection connection = null;
        Statement statement = null;

        try {
            //STEP 1: Register JDBS_Driver
            Class.forName(JDBC_DRIVER); //todo: Надо ли ?

            //STEP 2: Open a connection
            System.out.println("Connencting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);


            //STEP 3: Execute a query
            System.out.println("Creating table in given database...");
            statement = connection.createStatement();

//            try (PreparedStatement statement = DriverManager.getConnection(DB_URL, USER, PASS))

            JdbcConnectionPool.create(DB_URL, USER, PASS);

            String sqlCreate = String.join("\n",Files.readAllLines(Paths.get(Server.class.getResource("/data.sql").toURI())));

            try (PreparedStatement statement1 = connection.prepareStatement(sqlCreate)) {
                statement1.execute();
            } catch (Exception ex) {
                System.out.println(ex.fillInStackTrace());
            }

            //STEP 4: Clean-Up environment
            statement.close();
            connection.close();
        } catch (SQLException | URISyntaxException | IOException ex) {
            //Handle errors for JDBC

            ex.printStackTrace();
        }
    }
}
