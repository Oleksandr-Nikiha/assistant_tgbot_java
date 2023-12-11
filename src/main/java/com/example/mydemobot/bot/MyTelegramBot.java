package com.example.mydemobot.bot;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.mydemobot.bot.constans.Actions;
import com.example.mydemobot.bot.constans.Dictonares;
import com.example.mydemobot.bot.constans.Response;
import com.example.mydemobot.bot.constans.State;
import com.example.mydemobot.bot.keyboard.KeyboardList;
import com.example.mydemobot.bot.models.Accounting;
import com.example.mydemobot.bot.models.Users;
import com.example.mydemobot.bot.models.WeatherInfo;
import com.example.mydemobot.bot.services.CurrencyService;
import com.example.mydemobot.bot.services.FirebaseService;
import com.example.mydemobot.bot.services.MessageSenderService;
import com.example.mydemobot.bot.services.WeatherService;

@Component
public class MyTelegramBot extends TelegramLongPollingCommandBot {
    private final String username;

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private MessageSenderService messageSenderService;

    public MyTelegramBot(@Value("${bot.token}") String botToken, @Value("${bot.username}") String username) {
        super(botToken);
        this.username = username;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            User user = update.getCallbackQuery().getFrom();
            var userSession = firebaseService.getUser(user).getSession().getCurrentStep();
            var callbackQuery = update.getCallbackQuery();

            var checkCurrency = Dictonares.ACTION_CURR_LIST.stream()
                    .filter(object -> object.equals(callbackQuery.getData()))
                    .findFirst()
                    .orElse(null);

            var checkWeather = Dictonares.ACTION_CITY_LIST.stream()
                    .filter(object -> object.equals(callbackQuery.getData()))
                    .findFirst()
                    .orElse(null);

            var checkAccount = Dictonares.ACTION_FIN_LIST.stream()
                    .filter(object -> object.equals(callbackQuery.getData()))
                    .findFirst()
                    .orElse(null);

            try {
                if (checkCurrency != null && userSession.equals(State.CURRENCY_START)) {
                    currencyMessage(callbackQuery);
                } else if (checkWeather != null && userSession.equals(State.WEATHER_START)) {
                    weatherMessage(callbackQuery);
                } else if (checkAccount != null && userSession.equals(State.FIN_ACCOUNT_START)) {
                    financeStarted(callbackQuery);
                } else if (Actions.BACK.equals(callbackQuery.getData())) {
                    backMessage(callbackQuery);
                } else {
                    nothingToDo(callbackQuery);
                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            User user = update.getMessage().getFrom();
            var userSession = firebaseService.getUser(user).getSession().getCurrentStep();

            var message = update.getMessage();
            var text = message.getText();
            var command = getRegisteredCommand(text);

            try {
                if (Objects.nonNull(command)) {
                    command.processMessage(this, message, new String[] {});
                }else if (userSession.equals(State.SET_VALUE)) {
                    financeSetValue(message);
                }else if (userSession.equals(State.SET_ANN)) {
                    financeSetAnn(message);
                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PostConstruct
    private void initializeBotMessage() throws TelegramApiException{
        List<Users> admList = firebaseService.getAdmin();
        
        for (Users admin : admList) {
            sendApiMethod(messageSenderService.sendMessageReply(
                admin.getChatId(), 
                Response.START_BOT, 
                KeyboardList.HELLO_MARKUP));
        }
    }

    private void nothingToDo(CallbackQuery callbackQuery) throws TelegramApiException {
        sendApiMethod(messageSenderService.answerCallbackQuery(
            callbackQuery.getId(), 
            Response.NOTHING_TODO));
        sendApiMethod(messageSenderService.deleteMessage(
            callbackQuery.getMessage().getChatId(), 
            callbackQuery.getMessage().getMessageId()));
    }

    private void backMessage(CallbackQuery callbackQuery) throws TelegramApiException {
        firebaseService.updateUserStep(callbackQuery.getFrom(), State.GENERAL_MENU);

        sendApiMethod(messageSenderService.deleteMessage(
                callbackQuery.getMessage().getChatId(),
                callbackQuery.getMessage().getMessageId()));

        this.execute(messageSenderService.sendMessageReply(
                callbackQuery.getMessage().getChatId(),
                Response.BACK_MENU,
                KeyboardList.START_MARKUP));
    }

    private void currencyMessage(CallbackQuery callbackQuery) throws TelegramApiException {
        sendApiMethod(messageSenderService.editMessageTextInline(
                callbackQuery.getMessage().getChatId(),
                callbackQuery.getMessage().getMessageId(),
                String.format(Response.CURRENCY_INFO,
                        currencyService.getRoundedRateByCurrencyCode(callbackQuery.getData()),
                        callbackQuery.getData()),
                KeyboardList.CURRENCY_INL_MARKUP));
    }

    private void weatherMessage(CallbackQuery callbackQuery) throws TelegramApiException {
        WeatherInfo weatherObject = weatherService.getWeatherObject(callbackQuery.getData());

        sendApiMethod(messageSenderService.editMessageTextInline(
                callbackQuery.getMessage().getChatId(),
                callbackQuery.getMessage().getMessageId(),
                String.format(Response.WEATHER_INFO,
                        weatherObject.getCityMappedName(),
                        weatherObject.getWeatherType(),
                        weatherObject.getTemperature(),
                        weatherObject.getRelativeHumidity(),
                        weatherObject.getPressure(),
                        weatherObject.getWindSpeed(),
                        weatherObject.getLastUpdateDate()),
                KeyboardList.WEATHER_INL_MARKUP));
    }

    private void financeStarted(CallbackQuery callbackQuery) throws TelegramApiException {
        firebaseService.updateUserStep(callbackQuery.getFrom(), State.SET_VALUE);

        String responseText = null;
        InlineKeyboardMarkup responseKeyboard = null;

        if(callbackQuery.getData().equals(Actions.FIN_INCOME)) {
            responseText = Response.SET_INCOME;
            firebaseService.createdAccount(callbackQuery.getFrom(), callbackQuery.getData());
        }else if(callbackQuery.getData().equals(Actions.FIN_SPEND)) {
            responseText = Response.SET_SPEND;
            firebaseService.createdAccount(callbackQuery.getFrom(), callbackQuery.getData());
        }else if(callbackQuery.getData().equals(Actions.FIN_STATISTICK)) {
            
        }else{
            nothingToDo(callbackQuery);
        }

        sendApiMethod(messageSenderService.editMessageTextInline(
            callbackQuery.getMessage().getChatId(), 
            callbackQuery.getMessage().getMessageId(), 
            responseText, 
            responseKeyboard));
    }

    private void financeSetValue(Message message) throws TelegramApiException {
        firebaseService.updateUserStep(message.getFrom(), State.SET_ANN);
        firebaseService.setValueAccount(message.getFrom(), message.getText());
        
        sendApiMethod(messageSenderService.sendMessageReply(message.getChatId(), Response.SET_ANN, null));
    }
    
    private void financeSetAnn(Message message) throws TelegramApiException {
        firebaseService.updateUserStep(message.getFrom(), State.FIN_ACCOUNT_FINALLY);
        firebaseService.setAnnotAccount(message.getFrom(), message.getText());
        
        Accounting account = firebaseService.getLatestAccount(message.getFrom());

        sendApiMethod(messageSenderService.sendMessageReply(
            message.getChatId(), 
            String.format(Response.SUCCES_SET, 
                account.getParsedDate(),
                account.getValue(),
                account.getParsedType(),
                account.getAnnotation()
            ),
            KeyboardList.CONF_REJ_MARKUP));
    }
}