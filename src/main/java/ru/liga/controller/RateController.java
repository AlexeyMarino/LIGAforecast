package ru.liga.controller;

import ru.liga.service.Service;
import ru.liga.view.Console;

import java.util.Locale;

/**
 * Класс контроллера обрабатывающего запросы о прогнозе курсов валют
 */

public class RateController implements Controller {

    private final String[] commands;
    private final Console console;
    private final Service service;

    public RateController(String[] commands, Console console, Service service) {
        this.commands = commands;
        this.console = console;
        this.service = service;
    }

    @Override
    public void operate() {
        String currency;
        if (commands.length < 3) {
            console.wrongRateCommand();
            return;
        }

        switch (commands[1].toLowerCase(Locale.ROOT)) {
            case "usd" -> currency = "usd";
            case "eur" -> currency = "eur";
            case "try" -> currency = "try";
            default -> {
                console.wrongRateCommand();
                return;
            }
        }

        switch (commands[2].toLowerCase(Locale.ROOT)) {
            case "tomorrow" -> console.printDayRate(service.getDayRate(currency));
            case "week" -> console.printWeekRate(service.getWeekRate(currency));
            default -> console.wrongRateCommand();
        }

    }
}
