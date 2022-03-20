package ru.liga.controller;

import ru.liga.model.Answer;
import ru.liga.model.command.Command;

/**
 * Класс контроллера обрабатывающий команды системного характера
 */

public class SystemControllerImpl implements Controller {
    private final Command command;

    public SystemControllerImpl(Command command) {
        this.command = command;
    }


    @Override
    public Answer operate() {
        return (switch (command.getName()) {
            case HELP -> new Answer("""                
                    Cписок доступных комманд:
                        help     - помощь
                        rate     - прогноз курсов валют, с этой командой необходимо указывать валюту (USD, EUR, TRY),
                                       а так же период прогноза, на 1 день или на неделю (tomorrow или week)
                                       параметры команды необходимо указывать через пробел (пример: rate TRY tomorrow)
                        contacts - обратная связь
                        """);
            case CONTACTS -> new Answer("С вопросами, пожеланиями, информацией об ошибках просьба обращаться к @MarinoSpb");
            default -> throw new IllegalStateException("Unexpected value: " + command.getName());
        });
    }


}
