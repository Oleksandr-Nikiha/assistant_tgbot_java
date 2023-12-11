package com.example.mydemobot.bot.models;

import lombok.Data;

@Data
public class WeatherInfo {
    private String cityMappedName;
    private String cityDataName;
    private String weatherType;
    private String lastUpdateDate;
    private double temperature;
    private int relativeHumidity;
    private int pressure;
    private double windSpeed;
}
