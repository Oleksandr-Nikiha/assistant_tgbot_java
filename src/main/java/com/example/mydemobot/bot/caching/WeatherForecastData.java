package com.example.mydemobot.bot.caching;

import java.util.List;

import com.example.mydemobot.bot.models.WeatherForecast;

import lombok.Data;

@Data
public class WeatherForecastData {
    private List<WeatherForecast> data;
}
