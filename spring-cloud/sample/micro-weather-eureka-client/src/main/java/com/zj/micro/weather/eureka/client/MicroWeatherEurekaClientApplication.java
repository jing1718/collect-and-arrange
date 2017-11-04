package com.zj.micro.weather.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MicroWeatherEurekaClientApplication {

	@RequestMapping("/hello")
	public String hello() {
		return "Hello World";
	}

	@RequestMapping("/info")
	public String info() {
		return "micro-weather-eureka-client";
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroWeatherEurekaClientApplication.class, args);
	}
}
