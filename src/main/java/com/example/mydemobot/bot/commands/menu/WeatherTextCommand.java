package com.example.mydemobot.bot.commands.menu;

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
import com.example.mydemobot.bot.services.WeatherService;

@Component
public class WeatherTextCommand extends BaseTextCommand {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private WeatherService weatherService;

    public WeatherTextCommand(@Value(Commands.WEATHER_COMMAND) String textCommandIdentifier, @Value("") String description) {
        super(textCommandIdentifier, description);
    }
    
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        MessageSenderService messageSenderService = new MessageSenderService();
        firebaseService.updateUserStep(message.getFrom(), State.WEATHER_START);
        weatherService.initialize();
        
        try {
            absSender.execute(messageSenderService.sendMessageInline(
                message.getChatId(), 
                Response.WEATHER_MENU, 
                KeyboardList.WEATHER_INL_MARKUP
            ));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
