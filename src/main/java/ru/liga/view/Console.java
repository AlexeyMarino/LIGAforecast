package ru.liga.view;

import ru.liga.model.Rate;
import ru.liga.utils.DateTimeUtil;

import java.util.List;
import java.util.Scanner;

public class Console {
    private final Scanner scanner;

    public Console() {
        scanner = new Scanner(System.in);
    }

    public String insertCommand() {
        System.out.print("Введите команду: ");
        return scanner.nextLine();
    }

    public void printMessage(String text) {
        System.out.println(text);
    }

    public void printDayRate(Rate rate) {
        printMessage(String.format("%s - %s", rate.getDate().format(DateTimeUtil.printFormatter), String.format("%.2f", rate.getRate())));
    }


    public void printWeekRate(List<Rate> rates) {
        rates.forEach(this::printDayRate);
    }

    public void wrongRateCommand() {
        System.out.println("После команды \"rate\" необходимо вводить наименование валюты и период прогноза.");
        System.out.println("Для просмотра списка доступных команд введите \"help\"");
    }


}