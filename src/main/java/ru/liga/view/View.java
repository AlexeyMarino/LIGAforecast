package ru.liga.view;

public interface View {
    void printMessage(String text);

    void printMessage(Object answer, Long chatId);
}
