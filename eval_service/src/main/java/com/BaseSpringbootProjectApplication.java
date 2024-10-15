package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;


@SpringBootApplication
public class BaseSpringbootProjectApplication  {

	public static void main(String[] args) {
		SpringApplication.run(BaseSpringbootProjectApplication.class, args);
	}

}
