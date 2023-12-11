package com.example.mydemobot.bot.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Accounting {
    private Double value;
    private Timestamp accountDate;
    private String type;
    private String annotation;

    public Accounting() {
    }

    public String getParsedType() {
        switch (this.type) {
            case "Income":
            return "Дохід";

            case "Spending":
            return "Витрата";

            default:
            return "Невідомо";
        }
    }

    public String getParsedDate() {
        DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(this.accountDate.toDate());
    }
}
