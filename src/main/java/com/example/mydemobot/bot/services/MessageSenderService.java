package com.example.mydemobot.bot.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageSenderService {

        public EditMessageText editMessageTextInline(Long chatId, Integer messageId, String text,
                        InlineKeyboardMarkup keyboard) throws TelegramApiException {
                return EditMessageText.builder()
                                .chatId(chatId)
                                .messageId(messageId)
                                .replyMarkup(keyboard)
                                .text(text)
                                .build();
        }

        public DeleteMessage deleteMessage(Long chatId, Integer messageId) throws TelegramApiException {
                return DeleteMessage.builder()
                                .chatId(chatId)
                                .messageId(messageId)
                                .build();
        }

        public SendMessage sendMessageInline(Long chatId, String text, InlineKeyboardMarkup keyboard)
                        throws TelegramApiException {
                return SendMessage.builder()
                                .chatId(chatId)
                                .text(text)
                                .replyMarkup(keyboard)
                                .build();
        }

        public SendMessage sendMessageReply(Long chatId, String text, ReplyKeyboardMarkup keyboard)
                        throws TelegramApiException {
                return SendMessage.builder()
                                .chatId(chatId)
                                .text(text)
                                .replyMarkup(keyboard)
                                .build();
        }

        public AnswerCallbackQuery answerCallbackQuery(String queryId, String message) throws TelegramApiException {
                return AnswerCallbackQuery.builder()
                                .showAlert(true)
                                .callbackQueryId(queryId)
                                .text(message)
                                .build();
        }
}
