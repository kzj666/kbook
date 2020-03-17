package com.kk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.kk.dao")
//@ServletComponentScan(basePackages = "com.kk.filter")
public class KbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(KbookApplication.class, args);
	}

}
