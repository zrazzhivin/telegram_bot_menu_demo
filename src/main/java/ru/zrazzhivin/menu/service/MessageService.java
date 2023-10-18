package ru.zrazzhivin.menu.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final TelegramBot telegramBot;

    public MessageService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendTextMessage(long chatId, String messageToSend) {
        SendMessage sendMessage = new SendMessage(chatId, messageToSend);
        telegramBot.execute(sendMessage);
    }

    public void sendReplyMessage(long chatId, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage(chatId, "Выбери пункт из меню")
                .replyMarkup(keyboardMarkup);
        telegramBot.execute(sendMessage);
    }

    public ReplyKeyboardMarkup generateMenuKeyBoard(String... buttons) {
        return new ReplyKeyboardMarkup(buttons)
                .resizeKeyboard(true)
                .selective(true);
    }
}
