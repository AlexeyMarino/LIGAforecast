package ru.liga.utils;

import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Output;
import ru.liga.model.Period;
import ru.liga.model.command.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandParser {
    public Command getCommand(String command) {
        String[] splitCommands = command.split("-");

        List<String[]> commands = new ArrayList<>();
        for (String splitCommand : splitCommands) {
            commands.add(splitCommand.split(" "));
        }
        CommandName commandName;
        try {
            commandName = CommandName.valueOf(commands.get(0)[0].toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return new DefaultCommand(CommandName.ERROR, "Введена неверная команда: " + "\"" + commands.get(0)[0] + "\"");
        }

        if (commandName == CommandName.HELP || commandName == CommandName.CONTACTS) {
            return new SystemCommand(commandName);
        }

        if (commandName == CommandName.RATE) {
            if (splitCommands.length < 3) {
                return new DefaultCommand(CommandName.ERROR, "Для предсказания курса валют введите команду в формате rate USD -period week -alg moon -output list");
            }
            if (commands.get(0).length < 2) {
                return new DefaultCommand(CommandName.ERROR, "После команды \"rate\" необходимо вводить сокращенное наименование волюты (USD, EUR, TRY...)");
            }
            List<Currency> currencies = new ArrayList<>();
            String[] splitCurrencies = commands.get(0)[1].split(",");

            try {
                for (String splitCurrency : splitCurrencies) {
                    currencies.add(Currency.valueOf(splitCurrency));
                }
            } catch (IllegalArgumentException e) {
                return new DefaultCommand(CommandName.ERROR, "После команды \"rate\" необходимо вводить сокращенное наименование волюты (USD, EUR, TRY...)");
            }
            LocalDate date = null;
            if (commands.get(1).length != 2) {
                return new DefaultCommand(CommandName.ERROR, "После наименования волюты необходимо указать дату или период прогноза");
            }
            if (commands.get(1)[0].equalsIgnoreCase("date")) {
                if (commands.get(1)[1].equalsIgnoreCase("tomorrow")) {
                    date = LocalDate.now().plusDays(1);
                } else {
                    try {
                        date = LocalDate.parse(commands.get(1)[1], DateTimeUtil.PARSE_FORMATTER);
                    } catch (Exception e) {
                        return new DefaultCommand(CommandName.ERROR, "После команды \"data\" необходимо указать \"tomorrow\" " +
                                "или дату в формате \"dd.mm.yyyy\"");
                    }
                }
            }

            Period period = null;
            if (commands.get(1)[0].equalsIgnoreCase("period")) {
                try {
                    period = Period.valueOf(commands.get(1)[1].toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException e) {
                    return new DefaultCommand(CommandName.ERROR, "После команды \"period\" необходимо указать \"week\" или \"month\"");
                }
            }

            Algorithm algorithm;
            if (commands.get(2)[0].equalsIgnoreCase("alg")) {
                try {
                    algorithm = Algorithm.valueOf(commands.get(2)[1].toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException e) {
                    return new DefaultCommand(CommandName.ERROR, "После команды \"period\" необходимо указать \"week\" или \"month\"");
                }
            } else {
                return new DefaultCommand(CommandName.ERROR, "После выбора периода необходимо указать алгоритм прогноза (-alg actual)");
            }

            Output output = null;

            if (commands.size() == 4) {
                if (commands.get(3)[0].equalsIgnoreCase("output")) {
                    try {
                        output = Output.valueOf(commands.get(3)[1].toUpperCase(Locale.ROOT));
                    } catch (IllegalArgumentException e) {
                        return new DefaultCommand(CommandName.ERROR, "После выбора алгоритма расчета указывается способ отображения" +
                                "список или график (list, graph)");
                    }
                } else {
                    return new DefaultCommand(CommandName.ERROR, "После выбора алгоритма расчета указывается способ отображения" +
                            "список или график (-output list, -output graph)");
                }
            }
            return new RateCommand(currencies, date, period, algorithm, output);
        }
        return new DefaultCommand(CommandName.ERROR, "Неизвестнейшая ошибка, сорян");
    }

}
