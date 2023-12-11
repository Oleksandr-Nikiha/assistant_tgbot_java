package com.example.mydemobot.bot.models;

import java.util.List;

import lombok.Data;

@Data
public class WeatherForecast {
    private int code;
    private String day;
    private String night;
    private int icon;
    private List<LanguageForecast> languages;

    public WeatherForecast(){
    }

    @Data
    public static class LanguageForecast {
        private String lang_name;
        private String lang_iso;
        private String day_text;
        private String night_text;

        public LanguageForecast(){
        }
    }

    public String getConditionsDayNameByCode(String landCode, Integer isDay){
        for (LanguageForecast languageForecast : this.languages) {
            if (languageForecast.getLang_iso().equals(landCode)) {
                if(isDay == 1) {
                    return languageForecast.getDay_text();
                }else{
                    return languageForecast.getNight_text();
                }   
            }
        }
        return "Невідомий код";
    }
}
