package com.example.mydemobot.bot.caching;

import java.time.LocalDateTime;
import java.util.List;

import com.example.mydemobot.bot.models.WeatherInfo;

import lombok.Data;

@Data
public class WeatherData {
    private List<WeatherInfo> data;
    private LocalDateTime lastUpdated;
}
