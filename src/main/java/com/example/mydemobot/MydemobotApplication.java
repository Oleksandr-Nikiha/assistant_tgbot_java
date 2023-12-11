package com.example.mydemobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;


@SpringBootApplication
@ComponentScan(basePackages = "com.example.mydemobot")
@Import(TelegramBotStarterConfiguration.class)
public class MydemobotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydemobotApplication.class, args);
	}

}
