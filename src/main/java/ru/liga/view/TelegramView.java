package ru.liga.view;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.Bot;
import ru.liga.model.Currency;
import ru.liga.model.Output;
import ru.liga.model.Period;
import ru.liga.model.Rate;
import ru.liga.model.command.Command;
import ru.liga.model.command.RateCommand;
import ru.liga.utils.DateTimeUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public void printMessage(Object answer, Long chatId, Command command) {
        if (answer instanceof String) {
            sendText((String) answer, chatId);
            return;
        }

        String stringAnswer;
        RateCommand rateCommand = (RateCommand) command;


        if (answer instanceof Map<?, ?>) {
            Map<Currency, List<Rate>> mapRates = (Map<Currency, List<Rate>>) answer;

            if (((RateCommand) command).getOutput() == Output.LIST) {
                stringAnswer = printRates(mapRates, rateCommand.getCurrency().get(0));
                sendText(stringAnswer, chatId);
                return;
            }
            if (((RateCommand) command).getOutput() == Output.GRAPH) {
                int days = rateCommand.getPeriod() == Period.MONTH ? 30 : 7;
                sendPhoto(getGraph(mapRates, days), chatId);
            }

        }
    }


    public void sendText(String answer, Long chatId) {
        SendMessage message = new SendMessage();
        message.setText(answer);
        message.setChatId(chatId.toString());
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя userName !!!!!
        }
    }

    public void sendPhoto(File file, Long chatId) {
        SendPhoto photo = new SendPhoto();
        photo.setPhoto(new InputFile(file, "Graph"));
        photo.setChatId(chatId.toString());
        try {
            bot.execute(photo);
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя userName !!!!!
        }
    }


    private String printDayRate(Rate rate) {
        return String.format("%s - %s", rate.getDate().format(DateTimeUtil.PRINT_FORMATTER), String.format("%.2f", rate.getRate()));
    }


    private String printRates(Map<Currency, List<Rate>> ratesMap, Currency currency) {

        StringBuilder ratesString = new StringBuilder();
        for (Rate rate : ratesMap.get(currency)) {
            ratesString.append(printDayRate(rate));
            ratesString.append("\n");
        }
        return ratesString.toString();
    }

    public File getGraph(Map<Currency, List<Rate>> ratesMap, int days) {
        List<Double> x = NumpyUtils.linspace(1, days, days);
        Plot plt = Plot.create();
        for (List<Rate> currencyRates : ratesMap.values()) {
            List<Double> rates = currencyRates.stream()
                    .map(r -> r.getRate().doubleValue()).toList();
            plt.plot().add(x, rates);
        }
        plt.xlabel("Дата");
        plt.ylabel("Курс валюты");
        plt.savefig("src/main/resources/graph.png").dpi(200);
        try {
            plt.executeSilently();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }
//
        return new File("src/main/resources/graph.png");

    }

    @Override
    public void printMessage(String text) {

    }


}
