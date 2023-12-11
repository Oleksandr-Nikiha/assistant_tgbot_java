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
import com.example.mydemobot.bot.services.CurrencyService;
import com.example.mydemobot.bot.services.FirebaseService;
import com.example.mydemobot.bot.services.MessageSenderService;

@Component
public class CurrencyTextCommand extends BaseTextCommand {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private CurrencyService currencyService;

    public CurrencyTextCommand(@Value(Commands.CURRENCY_COMMAND) String textCommandIdentifier,
            @Value("") String description) {
        super(textCommandIdentifier, description);
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        MessageSenderService messageSenderService = new MessageSenderService();
        firebaseService.updateUserStep(message.getFrom(), State.CURRENCY_START);
        currencyService.initialize();

        try {
            absSender.execute(messageSenderService.sendMessageInline(
                message.getChatId(),
                Response.CURRENCY_MENU,
                KeyboardList.CURRENCY_INL_MARKUP));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
