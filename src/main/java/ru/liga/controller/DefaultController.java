package ru.liga.controller;

import ru.liga.model.command.Command;

/**
 * Класс контроллера для обработки команд непредусмотрееных приложением
 */
public class DefaultController implements Controller {
    private final Command command;

    public DefaultController(Command command) {
        this.command = command;
    }

    @Override
    public Object operate() {
        return command.getText();
    }


}
