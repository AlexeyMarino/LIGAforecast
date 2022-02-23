package ru.liga.controller;

import ru.liga.view.Console;

import java.util.Locale;

/**
 * Класс контроллера обрабатывающий команды системного характера
 */

public class SystemController implements Controller {
    private final Console console;
    private final String command;

    public SystemController(String command, Console console) {
        this.command = command;
        this.console = console;
    }

    @Override
    public void operate() {
        switch (command.toLowerCase(Locale.ROOT)) {
            case "help" -> console.printMessage("""
                                        
                    Cписок доступных комманд:
                        help     - помощь
                        rate     - прогноз курсов валют, с этой командой необходимо указывать валюту (USD, EUR, TRY),
                                       а так же период прогноза, на 1 день или на неделю (tomorrow или week)
                                       параметры команды необходимо указывать через пробел (пример: rate TRY tomorrow)
                        exit     - завершение программы
                        contacts - обратная связь
                        """);
            case "contacts" -> console.printMessage("С вопросами, пожеланиями, информацией об ошибках просьба обращаться к @MarinoSpb");
            case "exit" -> {
                console.printMessage("Application stopped");
                System.exit(0);
            }
        }
    }
}
