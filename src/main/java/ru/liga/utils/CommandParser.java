package ru.liga.utils;

import ru.liga.exception.InvalidCommandException;

import java.util.HashMap;
import java.util.Map;

import static ru.liga.exception.ExceptionMessage.INVALID_COMMAND;

public class CommandParser {
    public Map<String, String> parse(String text) {
        String[] splitText = text.split(" ");
        if (splitText.length % 2 != 0 && splitText.length != 1) {
            throw new InvalidCommandException(INVALID_COMMAND.getMessage());
        }
        Map<String, String> parseCommand = new HashMap<>();
        parseCommand.put("commandName", splitText[0]);
        if (splitText.length > 2) {
            parseCommand.put("currencies", splitText[1]);
            for (int i = 2; i < splitText.length; i++) {
                if (splitText[i].startsWith("-")) {
                    parseCommand.put(splitText[i], splitText[i + 1]);
                }
            }
        }
        return parseCommand;
    }
}
