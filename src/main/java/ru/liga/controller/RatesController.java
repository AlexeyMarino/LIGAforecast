package ru.liga.controller;

import ru.liga.service.Service;
import ru.liga.view.Console;

import java.util.Locale;

public class RatesController implements Controller {

    private final String[] commands;
    private final Console console;
    private final Service service;

    public RatesController(String[] commands, Console console, Service service) {
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
            case "euro" -> currency = "euro";
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
