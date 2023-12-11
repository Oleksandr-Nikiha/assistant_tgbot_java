package com.example.mydemobot.bot.models;

import java.util.List;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Users {
    private String userName;
    private Long id;
    private Long chatId;
    private Timestamp registerDate;
    private List<String> roles;
    private Sessions session;

    public Users() {
    }
}
