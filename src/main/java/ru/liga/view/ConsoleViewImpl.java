package ru.liga.view;

import ru.liga.model.Answer;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.model.command.Command;
import ru.liga.utils.DateTimeUtil;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleViewImpl implements View {
    private final Scanner scanner;

    public ConsoleViewImpl() {
        scanner = new Scanner(System.in);
    }

    public String insertCommand() {
        System.out.print("Введите команду: ");
        return scanner.nextLine();
    }

    @Override
    public void printMessage(Answer answer, Long chatId, Command command) {
        if (answer.getText() != null) {
            printMessage(answer.getText());
        } else printRates(answer.getRatesMap());
    }

    public void printMessage(String text) {
        System.out.println(text);
    }


    private void printDayRate(Rate rate) {
        printMessage(String.format("%s - %s", rate.getDate().format(DateTimeUtil.PRINT_DATE_FORMATTER_E_DD_MM_YYYY), String.format("%.2f", rate.getRate())));
    }


    private void printRates(Map<Currency, List<Rate>> ratesMap) {
        List<Rate> rates = ratesMap.values().stream().findFirst().orElse(null);
        assert rates != null;
        rates.forEach(this::printDayRate);
    }


}
