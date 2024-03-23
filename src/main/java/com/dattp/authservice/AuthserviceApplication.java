package com.dattp.authservice;


import com.dattp.authservice.config.GenDataConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AuthserviceApplication{

	public static void main(String[] args) {
		SpringApplication.run(AuthserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(GenDataConfig genDataConfig){
		return arg0->{
			genDataConfig.genData();
		};
	}
}
// https://www.youtube.com/watch?v=oGsQM6qjYQ8 56:43 