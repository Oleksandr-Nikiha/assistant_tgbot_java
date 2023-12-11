package com.example.mydemobot.bot.constans;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface Dictonares {
        List<String> ACTION_CURR_LIST = Arrays.asList(
                        "USD",
                        "EUR",
                        "PLN",
                        "GBP",
                        "CHF",
                        "JPY");

        Map<String, String> CITY_NAME_MAP = Map.of(
                        "Kyiv", "Київ",
                        "Vyshgorod", "Вишгород",
                        "Tetiyiv", "Тетіїв",
                        "Bila Tserkva", "Біла церква");

        List<String> ACTION_CITY_LIST = Arrays.asList(
                        "Kyiv",
                        "Vyshgorod",
                        "Tetiyiv",
                        "Bila Tserkva");

        List<String> CITY_NAME_LIST = Arrays.asList(
                        "Kyiv", "Vyshgorod", "Bila%20Tserkva", "Tetiyiv");

        List<String> ACTION_FIN_LIST = Arrays.asList(
                        "Income",
                        "Spending",
                        "Statistick");
}
