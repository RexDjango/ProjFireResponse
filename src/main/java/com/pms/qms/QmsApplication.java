package com.pms.qms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

//@ComponentScan("com.pms.qms.repository")
@SpringBootApplication
@EnableScheduling
public class QmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(QmsApplication.class, args);
	}

}
