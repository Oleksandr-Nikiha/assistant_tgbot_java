package com.example.mydemobot.bot.commands.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.mydemobot.bot.constans.Response;
import com.example.mydemobot.bot.constans.State;
import com.example.mydemobot.bot.keyboard.KeyboardList;
import com.example.mydemobot.bot.services.FirebaseService;
import com.example.mydemobot.bot.services.MessageSenderService;

@Component
public class MenuCommandHandler extends BotCommand {

    @Autowired
    private FirebaseService firebaseService;

    public MenuCommandHandler(@Value("menu") String commandIdentifier, @Value("") String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        MessageSenderService messageSenderService = new MessageSenderService();
        firebaseService.updateUserStep(user, State.GENERAL_MENU);
        
        try {
            absSender.execute(messageSenderService.sendMessageReply(
                chat.getId(), 
                String.format(Response.GENERAL_MENU, user.getFirstName()), 
                KeyboardList.START_MARKUP));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
