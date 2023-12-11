package com.example.mydemobot.bot.keyboard.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import com.example.mydemobot.bot.constans.Commands;

public interface ButtonsList {
    KeyboardButton GENERAL_MENU = KeyboardButton.builder()
        .text(Commands.GENERAL_MENU)
        .build();

    KeyboardButton SERVICE_MENU = KeyboardButton.builder()
        .text(Commands.SERVICE_MENU)
        .build();

    KeyboardButton WEATHER_BUTTON = KeyboardButton.builder()
        .text(Commands.WEATHER_COMMAND)
        .build();

    KeyboardButton CURRENCY_BUTTON = KeyboardButton.builder()
        .text(Commands.CURRENCY_COMMAND)
        .build();

    KeyboardButton FINANCIAL_ACCOUNTING = KeyboardButton.builder()
        .text(Commands.FINANCIAL_ACCOUNTING)
        .build();

    KeyboardButton CONFIRM = KeyboardButton.builder()
        .text(Commands.CONFIRM)
        .build();

    KeyboardButton REJECT = KeyboardButton.builder()
        .text(Commands.REJECT)
        .build();
}
