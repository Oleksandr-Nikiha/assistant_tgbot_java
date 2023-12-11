package com.example.mydemobot.bot.models;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Sessions {
    private String currentStep;
    private Timestamp actionDate;

    public Sessions() {
    }
}
