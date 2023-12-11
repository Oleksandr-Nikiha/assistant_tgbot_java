package com.example.mydemobot.bot.commands.auxiliary;

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
public class RejectTextCommand extends BaseTextCommand {
    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private MessageSenderService messageSenderService;

    public RejectTextCommand(@Value(Commands.REJECT) String textCommandIdentifier, @Value("") String description) {
        super(textCommandIdentifier, description);
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        String userState = firebaseService.getUser(message.getFrom()).getSession().getCurrentStep();

        //
        try {
            switch (userState){
                case State.FIN_ACCOUNT_FINALLY:
                    firebaseService.deleteLastAccount(message.getFrom());
                    firebaseService.updateUserStep(message.getFrom(), State.FIN_ACCOUNT_START);
                    absSender.execute(messageSenderService.sendMessageInline(
                        message.getChatId(),
                        Response.REJECT_SET,
                        KeyboardList.ACCOUNTING_INL_MARKUP));
                    break;

                default:
                    firebaseService.updateUserStep(message.getFrom(), State.GENERAL_MENU);
                    absSender.execute(messageSenderService.sendMessageReply(
                        message.getChatId(), 
                        Response.OOOPS, 
                        KeyboardList.START_MARKUP));
                    break;
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
