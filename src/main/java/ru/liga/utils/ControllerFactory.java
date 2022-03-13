package ru.liga.utils;

import ru.liga.controller.Controller;
import ru.liga.controller.DefaultController;
import ru.liga.controller.RateController;
import ru.liga.controller.SystemController;
import ru.liga.model.command.Command;
import ru.liga.repository.RatesRepository;

/**
 * Класс отвечающий за выбор контроллера, который будет работать с поступившей командой
 *
 * @see ru.liga.App
 */
public class ControllerFactory {

    public static Controller getController(Command command, RatesRepository repository) {

        return switch (command.getCommandName()) {
            case HELP, CONTACTS -> new SystemController(command);
            case RATE -> new RateController(command, repository);
            default -> new DefaultController(command);
        };
    }
}
