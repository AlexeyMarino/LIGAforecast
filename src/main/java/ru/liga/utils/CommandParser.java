package ru.liga.utils;

import ru.liga.exception.InvalidCommandException;
import ru.liga.exception.RepeatCommandParameter;

import java.util.HashMap;
import java.util.Map;

import static ru.liga.exception.ExceptionMessage.INVALID_COMMAND;
import static ru.liga.exception.ExceptionMessage.REPEAT_COMMAND_PARAMETER;

public class CommandParser {
    public Map<String, String> parse(String text) {
        if (text.isEmpty()) {
            throw new InvalidCommandException(INVALID_COMMAND.getMessage());
        }

        String[] splitText = text.split(" ");
        if (splitText.length % 2 != 0 && splitText.length != 1) {
            throw new InvalidCommandException(INVALID_COMMAND.getMessage());
        }
        Map<String, String> parseCommand = new HashMap<>();
        parseCommand.put("commandName", splitText[0]);
        if (splitText.length > 2) {
            parseCommand.put("currencies", splitText[1]);
            String checkRepeat = null;
            for (int i = 2; i < splitText.length; i++) {
                if (splitText[i].startsWith("-")) {
                    checkRepeat = parseCommand.putIfAbsent(splitText[i], splitText[i + 1]);
                }
                if (checkRepeat != null) {
                    throw new RepeatCommandParameter(REPEAT_COMMAND_PARAMETER.getMessage() + splitText[i]);
                }
            }
        }
        return parseCommand;
    }
}
