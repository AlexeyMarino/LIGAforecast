package ru.liga.model.command;

public interface Command {
    CommandName getCommandName();

    String getText();
}
