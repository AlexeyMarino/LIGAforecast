package ru.liga.model.command;

public class DefaultCommand implements Command {
    private final CommandName commandName;
    private final String text;

    public DefaultCommand(CommandName commandName, String text) {
        this.commandName = commandName;
        this.text = text;
    }

    @Override
    public CommandName getCommandName() {
        return commandName;
    }

    public String getText() {
        return text;
    }
}
