package ru.liga.controller;

import ru.liga.model.command.Command;

/**
 * Класс контроллера обрабатывающий команды системного характера
 */

public class SystemController implements Controller {
    private final Command command;

    public SystemController(Command command) {
        this.command = command;
    }


    @Override
    public Object operate() {
        return switch (command.getCommandName()) {
            case HELP -> """                
                    Cписок доступных комманд:
                        help     - помощь
                        rate     - прогноз курсов валют, с этой командой необходимо указывать валюту (USD, EUR, TRY),
                                       а так же период прогноза, на 1 день или на неделю (tomorrow или week)
                                       параметры команды необходимо указывать через пробел (пример: rate TRY tomorrow)
                        exit     - завершение программы
                        contacts - обратная связь
                        """;
            case CONTACTS -> "С вопросами, пожеланиями, информацией об ошибках просьба обращаться к @MarinoSpb";
            default -> "Упс";
        };
    }


}
