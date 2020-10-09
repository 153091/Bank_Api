package com.github.nikel90.bankapi;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

@SpringBootApplication
public class BankApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApiApplication.class, args);
	}

	@Bean
	public DataSource dataSource (@Value("${DB_URL}") String dbUrl,
									@Value("${DB_USER}") String dbUser,
									@Value("${DB_PASS}") String dbPass ) throws SQLException, FileNotFoundException, URISyntaxException {
		JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create(dbUrl, dbUser, dbPass);
		URL resource = BankApiApplication.class.getResource("/data.sql");
		RunScript.execute(jdbcConnectionPool.getConnection(), new FileReader(new File (resource.toURI())));

		return jdbcConnectionPool;
	}
}
