package ru.liga.view;

import ru.liga.model.command.Command;

public interface View {
    void printMessage(String text);

    void printMessage(Object answer, Long chatId, Command command);
}
