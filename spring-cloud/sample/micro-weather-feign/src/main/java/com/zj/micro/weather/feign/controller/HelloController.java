package com.zj.micro.weather.feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zj.micro.weather.feign.service.HelloService;

@RestController
public class HelloController {

	@Autowired
	private HelloService helloService;

	@RequestMapping("/hello")
	public String hello() {
		return helloService.getHello();
	}
}
