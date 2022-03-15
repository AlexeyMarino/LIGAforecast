package ru.liga.utils;

import ru.liga.exception.*;
import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Output;
import ru.liga.model.Period;
import ru.liga.model.command.Command;
import ru.liga.model.command.CommandName;

import java.time.LocalDate;
import java.util.*;

import static ru.liga.exception.ExceptionMessage.*;

public class CommandBuilder {
    public Command buildCommand(Map<String, String> splitCommand) {
        CommandName commandName = getCommandName(splitCommand.get("commandName"));

        if (commandName == CommandName.RATE) {
            List<Currency> currencies = getCurrencies(splitCommand.get("currencies"));

            Period period = getPeriod(splitCommand);

            Algorithm algorithm = getAlgorithm(splitCommand);

            Output output = getOutput(splitCommand);


            if (currencies.size() > 1 && output.equals(Output.LIST))
                throw new InvalidCommandException(ILLEGAL_LIST_OUTPUT.getMessage());

            if (!period.isPeriod() && output.equals(Output.GRAPH))
                throw new InvalidCommandException(ILLEGAL_GRAPH_OUTPUT.getMessage());

            return new Command(commandName, currencies, period, algorithm, output);
        } else return new Command(commandName);

    }

    private Output getOutput(Map<String, String> splitCommand) {
        Output output = null;
        if (splitCommand.containsKey("-output")) {
            try {
                output = Output.valueOf(splitCommand.get("-output").toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                throw new IllegalOutputException(ILLEGAL_OUTPUT.getMessage());
            }
        }
        return output;
    }

    private Algorithm getAlgorithm(Map<String, String> splitCommand) {
        Algorithm algorithm;
        try {
            algorithm = Algorithm.valueOf(splitCommand.get("-alg").toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new IllegalAlgorithmException(ILLEGAL_ALGORITHM.getMessage());
        }
        return algorithm;
    }


    private CommandName getCommandName(String name) {
        CommandName commandName;
        try {
            commandName = CommandName.valueOf(name.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException(INVALID_COMMAND.getMessage());
        }
        return commandName;
    }

    private List<Currency> getCurrencies(String currenciesString) {
        List<String> splitCurrencies = Arrays.stream(currenciesString.split(",")).distinct().toList();
        List<Currency> currencies = new ArrayList<>();

        try {
            for (String splitCurrency : splitCurrencies) {
                currencies.add(Currency.valueOf(splitCurrency.toUpperCase(Locale.ROOT)));
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalCurrencyTitleException(ILLEGAL_CURRENCY.getMessage());
        }

        return currencies;
    }

    private Period getPeriod(Map<String, String> splitCommand) {
        Period period = new Period();

        if (splitCommand.containsKey("-period") && splitCommand.containsKey("-date"))
            throw new InvalidCommandException(INVALID_COMMAND.getMessage());

        if (splitCommand.containsKey("-period")) {
            switch (splitCommand.get("-period").toLowerCase(Locale.ROOT)) {
                case "month" -> {
                    period.setDate(LocalDate.now().plusDays(30));
                    period.setPeriod(true);
                }
                case "week" -> {
                    period.setDate(LocalDate.now().plusDays(7));
                    period.setPeriod(true);
                }
                default -> throw new IllegalPeriodException(ILLEGAL_PERIOD.getMessage());
            }
        } else if (splitCommand.containsKey("-date")) {
            if (splitCommand.get("-date").equalsIgnoreCase("tomorrow")) {
                period.setDate(LocalDate.now().plusDays(1));
                period.setPeriod(false);
            } else {
                try {
                    period.setDate(LocalDate.parse(splitCommand.get("-date"), DateTimeUtil.PARSE_FORMATTER));
                    period.setPeriod(false);
                } catch (Exception e) {
                    throw new IllegalDateException(ILLEGAL_DATE.getMessage());
                }
            }
        } else
            throw new InvalidCommandException(INVALID_COMMAND.getMessage());

        return period;
    }
}
