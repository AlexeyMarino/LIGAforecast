package ru.liga.view;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.Bot;
import ru.liga.exception.PlottingException;
import ru.liga.model.Answer;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.model.command.Command;
import ru.liga.utils.DateTimeConstants;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static ru.liga.exception.ExceptionMessage.PLOTTING_ERROR;

@Slf4j
@AllArgsConstructor
public class TelegramViewImpl implements View {
    private final Bot bot;
    private final String FILE_PATH = "src/main/resources/graph.png";
    private final String FILE_PATH_NAME = "/graph.png";
    private final String FILE_NAME = "Graph";


    @Override
    public void printMessage(Answer answer, Long chatId, Command command) {
        if (answer.getText() != null) {
            sendText(answer.getText(), chatId);
        } else if (answer.isOutputGraph()) {
            sendPhoto(getGraph(answer.getRatesMap()), chatId);
        } else {
            sendText(printRates(answer.getRatesMap()), chatId);
        }
    }

    @Override
    public void sendText(String answer, Long chatId) {
        SendMessage message = new SendMessage();
        message.setText(answer);
        message.setChatId(chatId.toString());
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.debug("Не удалось отправить сообщение: " + e.getMessage());
        }
    }

    private void sendPhoto(String fileName, Long chatId) {
        SendPhoto photo = new SendPhoto();
        photo.setPhoto(new InputFile(TelegramViewImpl.class.getResourceAsStream(fileName), FILE_NAME));
        photo.setChatId(chatId.toString());
        try {
            bot.execute(photo);
        } catch (TelegramApiException e) {
            log.debug("Не удалось отправить график: " + e.getMessage());
        }
    }

    private String printDayRate(Rate rate) {
        return String.format("%s - %s", rate.getDate().format(DateTimeConstants.PRINT_DATE_FORMATTER_TO_VIEW), String.format("%.2f", rate.getRate()));
    }

    private String printRates(Map<Currency, List<Rate>> ratesMap) {
        StringBuilder ratesString = new StringBuilder();
        List<Rate> rates = ratesMap.values().stream().findFirst().orElse(null);
        assert rates != null;
        for (Rate rate : rates) {
            ratesString.append(printDayRate(rate)).append("\n");
        }
        return ratesString.toString();
    }

    private String getGraph(Map<Currency, List<Rate>> ratesMap) {

        int days = ratesMap.values().stream().findFirst().get().size();

        List<Double> x = NumpyUtils.linspace(1, days, days);
        Plot plt = Plot.create();
        for (List<Rate> currencyRates : ratesMap.values()) {
            List<Double> rates = currencyRates.stream()
                    .map(r -> r.getRate().doubleValue()).toList();
            plt.plot().add(x, rates);
        }
        //  plt.title();
        plt.xlabel("Дата");
        plt.ylabel("Курс валюты");
        plt.savefig(FILE_PATH).dpi(200);
        try {
            plt.executeSilently();
        } catch (IOException | PythonExecutionException e) {
            log.debug(PLOTTING_ERROR + e.getMessage());
            throw new PlottingException(PLOTTING_ERROR);
        }

        return FILE_PATH_NAME;
    }


}
