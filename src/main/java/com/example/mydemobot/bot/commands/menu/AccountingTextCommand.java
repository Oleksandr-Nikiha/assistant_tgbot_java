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

@Component
public class AccountingTextCommand extends BaseTextCommand {
    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private MessageSenderService messageSenderService;

    public AccountingTextCommand(@Value(Commands.FINANCIAL_ACCOUNTING) String textCommandIdentifier, @Value("") String description) {
        super(textCommandIdentifier, description);
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        firebaseService.updateUserStep(message.getFrom(), State.FIN_ACCOUNT_START);

        try {
            absSender.execute(messageSenderService.sendMessageInline(
                message.getChatId(),
                Response.ACCOUNTING_START,
                KeyboardList.ACCOUNTING_INL_MARKUP));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
