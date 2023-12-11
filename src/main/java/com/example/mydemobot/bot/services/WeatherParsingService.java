package com.example.mydemobot.bot.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.mydemobot.bot.caching.WeatherForecastData;
import com.example.mydemobot.bot.models.WeatherForecast;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Service
public class WeatherParsingService {
    private WeatherForecastData cachedData;
    private List<WeatherForecast> weatherForecastList;

    @Value("${weather.parsing_url}")
    private String weatherParsingUrl;

    public WeatherParsingService() {
        cachedData = new WeatherForecastData();
    }

    @PostConstruct
    public void initialize() throws MalformedURLException, IOException {
        weatherForecastList = loadWeatherForecasts();
    }

    public WeatherForecast getWeatherForecastObject(Integer codeWeather){
        if(weatherForecastList == null) {
            return null;
        }

        WeatherForecast weatherForecast = weatherForecastList.stream()
            .filter(weather -> weather.getCode() == codeWeather)
            .findFirst()
            .orElse(null);

        return weatherForecast;
    }

    private List<WeatherForecast> loadWeatherForecasts() throws MalformedURLException, IOException{
        List<WeatherForecast> dataList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(new URL(weatherParsingUrl));
        for (JsonNode object : json){
            dataList.add(objectMapper.convertValue(object, WeatherForecast.class));
        }
        
        cachedData.setData(dataList);
        return cachedData.getData();
    }
}
