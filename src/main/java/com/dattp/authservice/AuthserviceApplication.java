package com.dattp.authservice;


import com.dattp.authservice.config.GenDataConfig;
import com.dattp.authservice.service.TelegramService;
import com.dattp.authservice.utils.DateUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.format.DateTimeFormatter;


@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
public class AuthserviceApplication{

	public static void main(String[] args) {
		SpringApplication.run(AuthserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(GenDataConfig genDataConfig, TelegramService telegramService){
		return arg0->{
//			genDataConfig.genData();
			String message =
				DateUtils.getcurrentLocalDateTime()
					.plusHours(7)
					.format(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd"))
					+": AUTH ===> STARTED";
			telegramService.sendNotificationMonitorSystem(message);
		};
	}
}
// https://www.youtube.com/watch?v=oGsQM6qjYQ8 56:43 