package com.example.mydemobot.bot.services;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.mydemobot.bot.caching.WeatherData;
import com.example.mydemobot.bot.constans.Dictonares;
import com.example.mydemobot.bot.models.WeatherInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Service
public class WeatherService {
    private WeatherData cachedData;
    private List<WeatherInfo> weatherInfoList;

    @Autowired
    private WeatherParsingService weatherParsingService;

    @Value("${weather.url}")
    private String weatherUrl;

    @Value("${weather.token}")
    private String weatherToken;

    public WeatherService() {
        cachedData = new WeatherData();
        cachedData.setLastUpdated(null);
    }

    @PostConstruct
    public void initialize() {
        weatherInfoList = loadWeatherInfo();
    }

    public WeatherInfo getWeatherObject(String cityName) {
        if (weatherInfoList == null) {
            return null;
        }

        WeatherInfo weatherInfo = weatherInfoList.stream()
                .filter(weather -> weather.getCityDataName().contains(cityName))
                .findFirst()
                .orElse(null);

        return weatherInfo;
    }

    private List<WeatherInfo> loadWeatherInfo() {
        if (isDataStale()) {
            List<WeatherInfo> dataList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            Dictonares.CITY_NAME_LIST.forEach(cityName -> {
                
                try {
                    URL url = new URL(weatherUrl + cityName + String.format("&key=%s", weatherToken));
                    JsonNode jsonNode = objectMapper.readTree(url);

                    String cityDataName = jsonNode.path("location").path("name").asText();
                    int weatherType = jsonNode.path("current").path("condition").path("code").asInt();
                    String dateTime = jsonNode.path("current").path("last_updated").asText();
                    double temperature = jsonNode.path("current").path("temp_c").asDouble();
                    int relativeHumidity = jsonNode.path("current").path("humidity").asInt();
                    int pressure = jsonNode.path("current").path("pressure_mb").asInt();
                    double windSpeed = jsonNode.path("current").path("wind_kph").asDouble();
                    int isDay = jsonNode.path("current").path("is_day").asInt();

                    WeatherInfo weatherObject = new WeatherInfo();
                    weatherObject.setCityDataName(cityDataName);
                    weatherObject.setCityMappedName(Dictonares.CITY_NAME_MAP.get(cityDataName));
                    weatherObject.setWeatherType(weatherParsingService.getWeatherForecastObject(weatherType)
                            .getConditionsDayNameByCode("uk", isDay));
                    weatherObject.setLastUpdateDate(dateTime);
                    weatherObject.setPressure(pressure);
                    weatherObject.setRelativeHumidity(relativeHumidity);
                    weatherObject.setTemperature(temperature);
                    weatherObject.setWindSpeed(windSpeed);

                    dataList.add(weatherObject);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            cachedData.setData(dataList);
            cachedData.setLastUpdated(LocalDateTime.now());
        }
        return cachedData.getData();
    }

    private boolean isDataStale() {
        if (cachedData.getLastUpdated() == null) {
            return true;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastUpdated = cachedData.getLastUpdated();
        Duration duration = Duration.between(lastUpdated, currentTime);

        return duration.toHours() >= 1;
    }
}
