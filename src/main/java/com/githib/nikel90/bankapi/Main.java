package com.githib.nikel90.bankapi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
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
    static final String DB_URL = "jdbc:h2:~/test";

    static final String USER = "sa";
    static final String PASS = "";

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
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connencting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);


            //STEP 3: Execute a query
            System.out.println("Creating table in given database...");
            statement = connection.createStatement();


            String sqlCreate = String.join("\n",Files.readAllLines(Paths.get(Server.class.getResource("/CreateTables.sql").toURI())));

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
            //Тут закрыть статмен и конект???????????????? try with resources
            ex.printStackTrace();
        }
    }
}
