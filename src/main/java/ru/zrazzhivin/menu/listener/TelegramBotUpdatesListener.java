package ru.zrazzhivin.menu.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Service;
import ru.zrazzhivin.menu.menu.BotMessages;
import ru.zrazzhivin.menu.service.MessageService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final MessageService messageService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, MessageService messageService) {
        this.telegramBot = telegramBot;
        this.messageService = messageService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            Long chatId = update.message().chat().id();
            String text = update.message().text();

            if ("/start".equals(text)) {
                ReplyKeyboardMarkup replyKeyboardMarkup = messageService.generateMenuKeyBoard(
                        BotMessages.CALL_VOLUNTEER,
                        BotMessages.INFO,
                        BotMessages.HOW_TO_ADOPT,
                        BotMessages.SEND_REPORT
                );
                messageService.sendReplyMessage(chatId, replyKeyboardMarkup);
            } else {
                // в переменной text лежит текст из кнопки, которую нажал пользователь
                // нужно сравнивать и обрабатывать
                messageService.sendTextMessage(chatId, "Выбранная опция: " + text);
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
