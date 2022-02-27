package ru.liga.utils;

import ru.liga.controller.Controller;
import ru.liga.controller.DefaultController;
import ru.liga.controller.RateController;
import ru.liga.controller.SystemController;
import ru.liga.service.ForecastService;
import ru.liga.view.Console;

import java.util.Locale;

/**
 * Класс отвечающий за выбор контроллера, который будет работать с поступившей командой
 * @see ru.liga.App
 */
public class ControllerSelection {

    public static Controller getController(String command, ForecastService service, Console console) {
        String[] commands = command.split(" ");

        return switch (commands[0].toLowerCase(Locale.ROOT)) {
            case "help", "contacts", "exit" -> new SystemController(commands[0], console);
            case "rate" -> new RateController(commands, console, service);
            default -> new DefaultController(command, console);
        };
    }
}
