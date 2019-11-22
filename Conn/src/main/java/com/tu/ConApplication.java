package com.tu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tu.mapper")
public class ConApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConApplication.class, args);
	}

}
