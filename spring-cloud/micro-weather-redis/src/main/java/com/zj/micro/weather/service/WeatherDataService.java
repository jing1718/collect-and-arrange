package com.zj.micro.weather.service;

import com.zj.micro.weather.vo.WeatherResponse;

public interface WeatherDataService {
	WeatherResponse getDataByCityId(String cityId);

	WeatherResponse getDataByCityName(String cityName);
}
