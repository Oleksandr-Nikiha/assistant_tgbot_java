package com.example.mydemobot.bot.commands.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.mydemobot.bot.commands.BaseTextCommand;
import com.example.mydemobot.bot.constans.Commands;
import com.example.mydemobot.bot.constans.Response;
import com.example.mydemobot.bot.constans.State;
import com.example.mydemobot.bot.keyboard.KeyboardList;
import com.example.mydemobot.bot.services.FirebaseService;
import com.example.mydemobot.bot.services.MessageSenderService;

@Component
public class GeneralMenuTextCommand extends BaseTextCommand{
    
    @Autowired
    private FirebaseService firebaseService;

    public GeneralMenuTextCommand(@Value(Commands.GENERAL_MENU) String commandIdentifier, @Value("") String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        MessageSenderService messageSenderService = new MessageSenderService();
        firebaseService.updateUserStep(message.getFrom(), State.GENERAL_MENU);
        
        try {
            absSender.execute(messageSenderService.sendMessageReply(
                message.getChatId(), 
                String.format(Response.GENERAL_MENU, message.getFrom().getFirstName()), 
                KeyboardList.START_MARKUP));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
