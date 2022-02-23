package ru.liga.controller;

import ru.liga.view.Console;

import java.util.Locale;

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
                        help - помощь
                        contacts - обратная связь
                        rate - прогноз курсов валют, с этой командой необходимо указывать валюту (USD, EURO, TRY),
                        а так же период прогноза, на 1 день или на неделю (tomorrow или week)
                        параметры команды необходимо указывать через пробел (пример: rate TRY tomorrow)
                        """);
            case "contacts" -> console.printMessage("С вопросами, пожеланиями, информацией об ошибках просьба обращаться к @MarinoSpb");
        }
    }
}
