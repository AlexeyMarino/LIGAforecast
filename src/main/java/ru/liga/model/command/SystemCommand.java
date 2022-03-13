package ru.liga.model.command;

public class SystemCommand implements Command {
    private final CommandName commandName;

    public SystemCommand(CommandName commandName) {
        this.commandName = commandName;
    }

    @Override
    public CommandName getCommandName() {
        return commandName;
    }

    @Override
    public String getText() {
        return null;
    }
}
