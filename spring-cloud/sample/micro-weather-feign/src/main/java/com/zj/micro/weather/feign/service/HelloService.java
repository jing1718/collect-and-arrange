package com.zj.micro.weather.feign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("micro-weather-eureka-client")
public interface HelloService {
	@RequestMapping("/hello")
	String getHello();
}
