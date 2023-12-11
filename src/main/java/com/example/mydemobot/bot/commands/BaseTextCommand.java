package com.example.mydemobot.bot.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class BaseTextCommand implements IBotCommand {
    private final String textCommandIdentifier;
    private final String description;

    public BaseTextCommand(String textCommandIdentifier, String description) {
        this.textCommandIdentifier = textCommandIdentifier;
        this.description = description;
    }

    @Override
    public String getCommandIdentifier() {
        return textCommandIdentifier;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        throw new UnsupportedOperationException("Unimplemented method 'processMessage'");
    }
    
}
