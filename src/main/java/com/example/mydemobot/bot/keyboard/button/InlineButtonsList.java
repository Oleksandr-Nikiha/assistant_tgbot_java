package com.example.mydemobot.bot.keyboard.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.example.mydemobot.bot.constans.Actions;
import com.example.mydemobot.bot.constans.Commands;

public interface InlineButtonsList {
        InlineKeyboardButton USD = InlineKeyboardButton.builder()
                        .text(Commands.CURR_USD)
                        .callbackData(Actions.CURR_USD)
                        .build();

        InlineKeyboardButton EUR = InlineKeyboardButton.builder()
                        .text(Commands.CURR_EUR)
                        .callbackData(Actions.CURR_EUR)
                        .build();

        InlineKeyboardButton PLN = InlineKeyboardButton.builder()
                        .text(Commands.CURR_PLN)
                        .callbackData(Actions.CURR_PLN)
                        .build();

        InlineKeyboardButton GBP = InlineKeyboardButton.builder()
                        .text(Commands.CURR_GBP)
                        .callbackData(Actions.CURR_GBP)
                        .build();

        InlineKeyboardButton CHF = InlineKeyboardButton.builder()
                        .text(Commands.CURR_CHF)
                        .callbackData(Actions.CURR_CHF)
                        .build();

        InlineKeyboardButton JPY = InlineKeyboardButton.builder()
                        .text(Commands.CURR_JPY)
                        .callbackData(Actions.CURR_JPY)
                        .build();

        InlineKeyboardButton KYIV = InlineKeyboardButton.builder()
                        .text(Commands.CITY_KYIV)
                        .callbackData(Actions.CITY_KYIV)
                        .build();

        InlineKeyboardButton VYSHGOROD = InlineKeyboardButton.builder()
                        .text(Commands.CITY_VYSHGOROD)
                        .callbackData(Actions.CITY_VYSHGOROD)
                        .build();

        InlineKeyboardButton TETIIV = InlineKeyboardButton.builder()
                        .text(Commands.CITY_TETIIV)
                        .callbackData(Actions.CITY_TETIIV)
                        .build();
        
        InlineKeyboardButton BILACERKVA = InlineKeyboardButton.builder()
                        .text(Commands.CITY_BC)
                        .callbackData(Actions.CITY_BC)
                        .build();

        InlineKeyboardButton INCOME = InlineKeyboardButton.builder()
                        .text(Commands.FIN_INCOME)
                        .callbackData(Actions.FIN_INCOME)
                        .build();

        InlineKeyboardButton SPEND = InlineKeyboardButton.builder()
                        .text(Commands.FIN_SPEND)
                        .callbackData(Actions.FIN_SPEND)
                        .build();

        InlineKeyboardButton STATISTICK = InlineKeyboardButton.builder()
                        .text(Commands.FIN_STATISTICK)
                        .callbackData(Actions.FIN_STATISTICK)
                        .build();

        InlineKeyboardButton BACK = InlineKeyboardButton.builder()
                        .text(Commands.BACK)
                        .callbackData(Actions.BACK)
                        .build();
}
