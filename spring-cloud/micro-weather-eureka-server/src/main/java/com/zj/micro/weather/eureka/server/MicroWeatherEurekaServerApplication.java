package com.zj.micro.weather.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaServer
@RestController
public class MicroWeatherEurekaServerApplication {

	@RequestMapping("/info")
	public String info() {
		return "micro-weather-eureka-server";
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroWeatherEurekaServerApplication.class, args);
	}
}
