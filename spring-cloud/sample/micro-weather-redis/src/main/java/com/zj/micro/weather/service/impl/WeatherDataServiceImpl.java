package com.zj.micro.weather.service.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.micro.weather.service.WeatherDataService;
import com.zj.micro.weather.vo.WeatherResponse;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private final String WEATHER_API = "http://wthrcdn.etouch.cn/weather_mini";

	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = WEATHER_API + "?citykey=" + cityId;
		return this.doGetWeatherData(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String uri = WEATHER_API + "?city=" + cityName;
		return this.doGetWeatherData(uri);
	}

	private WeatherResponse doGetWeatherData(String uri) {
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		String strBody = null;
		if (stringRedisTemplate.hasKey(uri)) {
			System.out.println("Found key = " + uri + ", value = " + ops.get(uri));
			strBody = ops.get(uri);
		} else {
			System.out.println("Not found key = " + uri);

			ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
			
			if (response.getStatusCodeValue() == 200) {
				strBody = response.getBody();
			}
			ops.set(uri, strBody, 10L, TimeUnit.SECONDS);
		}

		ObjectMapper mapper = new ObjectMapper();
		WeatherResponse weather = null;

		try {
			weather = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return weather;
	}

}
