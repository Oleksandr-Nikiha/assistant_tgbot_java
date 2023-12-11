package com.example.mydemobot.bot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.mydemobot.bot.caching.CurrencyData;
import com.example.mydemobot.bot.models.CurrencyInfo;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyService {
    private CurrencyData cachedData;
    private List<CurrencyInfo> currencyInfoList;

    @Value("${nbu.url}")
    private String nbuUrl;

    
    public CurrencyService() {
        cachedData = new CurrencyData();
        cachedData.setLastUpdated(null); // Перший запуск, дані застарілі
    }

    @PostConstruct
    public void initialize() {
        currencyInfoList = loadCurrencyInfo();
    }

    public double getRoundedRateByCurrencyCode(String currencyCode) {
        if (currencyInfoList == null) {
            return 0.0; // Повертати 0.0 в разі помилки
        }

        CurrencyInfo currencyInfo = currencyInfoList.stream()
                .filter(info -> info.getCc().equals(currencyCode))
                .findFirst()
                .orElse(null);

        if (currencyInfo != null) {
            return Math.round(currencyInfo.getRate() * 100.0) / 100.0;
        } else {
            return 0.0;
        }
    }

    private List<CurrencyInfo> loadCurrencyInfo() {
        if (isDataStale()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(new URL(nbuUrl));
                
                List<CurrencyInfo> currencyInfoList = Arrays.asList(objectMapper.readValue(jsonNode.toString(), CurrencyInfo[].class));

                cachedData.setData(currencyInfoList);
                cachedData.setLastUpdated(LocalDateTime.now());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
        
        // Перевірте, чи минуло, наприклад, 1 година, і поверніть true, якщо дані застаріли
        return duration.toHours() >= 1;
    }
}
