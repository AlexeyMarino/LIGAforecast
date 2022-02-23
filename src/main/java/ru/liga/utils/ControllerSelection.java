package ru.liga.utils;

import ru.liga.controller.Controller;
import ru.liga.controller.DefaultController;
import ru.liga.controller.RatesController;
import ru.liga.controller.SystemController;

import java.util.Locale;

public class ControllerSelection {
    public static Controller getController(String command) {
        if (command == null) {
            return new DefaultController();
        }
        switch (command.toLowerCase(Locale.ROOT)) {
            case "help":
                SystemController systemController = new SystemController();

                return systemController;
            case "rate":
                RatesController ratesController = new RatesController();
                return ratesController;
            default:
                return new DefaultController();
        }
    }
}
