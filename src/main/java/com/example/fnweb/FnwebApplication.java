package com.example.fnweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
@MapperScan("com.example.fnweb.mapper")
public class FnwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(FnwebApplication.class, args);
	}
}
