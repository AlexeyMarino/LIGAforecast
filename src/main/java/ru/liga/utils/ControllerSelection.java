package ru.liga.utils;

import ru.liga.controller.Controller;
import ru.liga.controller.DefaultController;
import ru.liga.controller.RatesController;
import ru.liga.controller.SystemController;
import ru.liga.service.Service;
import ru.liga.view.Console;

import java.util.Locale;

public class ControllerSelection {
    public static Controller getController(String command, Service service, Console console) {

        String[] commands = command.split(" ");

        return switch (commands[0].toLowerCase(Locale.ROOT)) {
            case "help", "contacts" -> new SystemController(commands[0], console);
            case "rate" -> new RatesController(commands, console, service);
            default -> new DefaultController(command, console);
        };
    }
}
