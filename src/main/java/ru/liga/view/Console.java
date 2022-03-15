package ru.liga.view;

import ru.liga.model.Answer;
import ru.liga.model.Rate;
import ru.liga.model.command.Command;
import ru.liga.utils.DateTimeUtil;

import java.util.List;
import java.util.Scanner;

public class Console implements View {
    private final Scanner scanner;

    public Console() {
        scanner = new Scanner(System.in);
    }

    public String insertCommand() {
        System.out.print("Введите команду: ");
        return scanner.nextLine();
    }

    @Override
    public void printMessage(Answer answer, Long chatId, Command command) {

    }

    public void printMessage(String text) {
        System.out.println(text);
    }


    private void printDayRate(Rate rate) {
        printMessage(String.format("%s - %s", rate.getDate().format(DateTimeUtil.PRINT_FORMATTER), String.format("%.2f", rate.getRate())));
    }


    private void printRates(List<Rate> rates) {
        rates.forEach(this::printDayRate);
    }

    @Override
    public void printMessage(Object answer, Long chatId, Command command) {

    }


}
