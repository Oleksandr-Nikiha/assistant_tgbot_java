package com.example.mydemobot.bot.keyboard;

import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.example.mydemobot.bot.keyboard.button.ButtonsList;
import com.example.mydemobot.bot.keyboard.button.InlineButtonsList;

public interface KeyboardList {
        ReplyKeyboardMarkup HELLO_MARKUP = ReplyKeyboardMarkup.builder()
                        .resizeKeyboard(true)
                        .oneTimeKeyboard(true)
                        .keyboardRow(new KeyboardRow(List.of(
                                ButtonsList.GENERAL_MENU
                        )))
                        .keyboardRow(new KeyboardRow(List.of(
                                ButtonsList.SERVICE_MENU
                        )))
                        .build();

        ReplyKeyboardMarkup START_MARKUP = ReplyKeyboardMarkup.builder()
                        .resizeKeyboard(true)
                        .oneTimeKeyboard(true)
                        .keyboardRow(new KeyboardRow(List.of(
                                ButtonsList.CURRENCY_BUTTON,
                                ButtonsList.WEATHER_BUTTON)))
                        .keyboardRow(new KeyboardRow(List.of(
                                ButtonsList.FINANCIAL_ACCOUNTING)))
                        .build();
        
        ReplyKeyboardMarkup CONF_REJ_MARKUP = ReplyKeyboardMarkup.builder()
                        .resizeKeyboard(true)
                        .oneTimeKeyboard(true)
                        .keyboardRow(new KeyboardRow(List.of(
                                ButtonsList.CONFIRM
                        )))
                        .keyboardRow(new KeyboardRow(List.of(
                                ButtonsList.REJECT
                        )))
                        .build();

        InlineKeyboardMarkup CURRENCY_INL_MARKUP = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(
                                InlineButtonsList.USD,
                                InlineButtonsList.EUR,
                                InlineButtonsList.PLN))
                        .keyboardRow(List.of(
                                InlineButtonsList.GBP,
                                InlineButtonsList.CHF,
                                InlineButtonsList.JPY))
                        .keyboardRow(List.of(
                                InlineButtonsList.BACK))
                        .build();

        InlineKeyboardMarkup WEATHER_INL_MARKUP = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(
                                InlineButtonsList.KYIV,
                                InlineButtonsList.VYSHGOROD,
                                InlineButtonsList.TETIIV))
                        .keyboardRow(List.of(
                                InlineButtonsList.BILACERKVA))
                        .keyboardRow(List.of(
                                InlineButtonsList.BACK))
                        .build();

        InlineKeyboardMarkup ACCOUNTING_INL_MARKUP = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(
                                InlineButtonsList.INCOME,
                                InlineButtonsList.SPEND))
                        .keyboardRow(List.of(
                                InlineButtonsList.STATISTICK))
                        .keyboardRow(List.of(
                                InlineButtonsList.BACK))
                        .build();
}
