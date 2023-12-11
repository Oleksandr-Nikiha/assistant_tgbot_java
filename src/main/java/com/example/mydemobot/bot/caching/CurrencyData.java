package com.example.mydemobot.bot.caching;

import java.time.LocalDateTime;
import java.util.List;

import com.example.mydemobot.bot.models.CurrencyInfo;

import lombok.Data;

@Data
public class CurrencyData {
    private List<CurrencyInfo> data;
    private LocalDateTime lastUpdated;
}
