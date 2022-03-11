package ru.liga.view;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.Bot;
import ru.liga.model.Rate;
import ru.liga.utils.DateTimeUtil;

import java.util.List;

public class TelegramView implements View {
    private final Bot bot;

    public TelegramView(Bot bot) {
        this.bot = bot;
    }

    public Message getMessage() {
        Update update = null;
        try {
            update = (Update) bot.getReceiveQueue().take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return update.getMessage();
    }


    @Override
    public void printMessage(Object answer, Long chatId) {
        SendMessage message = new SendMessage();
        if (answer instanceof String) {
            sendText(message, (String) answer, chatId);
        }

        String stringAnswer;
        if (answer instanceof List<?>) {
            stringAnswer = printRates((List<Rate>) answer);
            sendText(message, stringAnswer, chatId);
        }
    }

    public void sendText(SendMessage message, String answer, Long chatId) {
        message.setText(answer);
        message.setChatId(chatId.toString());
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя userName !!!!!
        }
    }


    private String printDayRate(Rate rate) {
        return String.format("%s - %s", rate.getDate().format(DateTimeUtil.PRINT_FORMATTER), String.format("%.2f", rate.getRate()));
    }


    private String printRates(List<Rate> rates) {

        StringBuilder ratesString = new StringBuilder();
            for (Rate rate : rates) {
                ratesString.append(printDayRate(rate));
                ratesString.append("\n");
            }
        return ratesString.toString();
    }

    @Override
    public void printMessage(String text) {

    }


}
