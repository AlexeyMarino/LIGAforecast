package ru.liga.utils;

import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Output;
import ru.liga.model.Period;
import ru.liga.model.command.Command;
import ru.liga.model.command.CommandName;
import ru.liga.model.command.RateCommand;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandParser2 {
    public Command getCommand(String text) {

        String[] splitCommands = text.split("\\s");
        String commandName = splitCommands[0];
        String currencies = splitCommands[1];
        String period = splitCommands[2];
        String periodValue = splitCommands[3];
        String alg = splitCommands[4];
        String algValue = splitCommands[5];
        String output = null;
        String outputValue = null;
        if (splitCommands.length == 8) {
            output = splitCommands[6];
            outputValue = splitCommands[7];
        }

        return switch (CommandName.valueOf(commandName.toUpperCase(Locale.ROOT))) {
            case HELP, CONTACTS -> getRateCommand(currencies, period, periodValue, alg, algValue, output, outputValue);
            case RATE -> getRateCommand(currencies, period, periodValue, alg, algValue, output, outputValue);
            default -> getRateCommand(currencies, period, periodValue, alg, algValue, output, outputValue);
        };


    }

    private Command getRateCommand(String currencies, String period, String periodValue, String alg, String algValue, String output, String outputValue) {
        RateCommand rateCommand = new RateCommand();
        rateCommand.setCurrency(getCurrencies(currencies));
        switch (period) {
            case "-period" -> {
                rateCommand.setPeriod(Period.valueOf(periodValue.toUpperCase(Locale.ROOT)));
                rateCommand.setDate(null);
            }
            case "-date" -> {
                rateCommand.setDate(LocalDate.parse(periodValue, DateTimeUtil.PARSE_FORMATTER));
                rateCommand.setPeriod(null);
            }
        }
        if (alg.equals("-alg")) {
            switch (algValue) {
                case "lr" -> rateCommand.setAlgorithm(Algorithm.LR);
                case "actual" -> rateCommand.setAlgorithm(Algorithm.ACTUAL);
                case "moon" -> rateCommand.setAlgorithm(null);
            }
        }
        if (output.equals("-output")) {
            switch (outputValue) {
                case "list" -> rateCommand.setOutput(Output.LIST);
                case "graph" -> rateCommand.setOutput(Output.GRAPH);
            }
        }
        return rateCommand;
    }

    private List<Currency> getCurrencies(String currencies) {
        String[] splitCurrencies = currencies.split(",");
        List<Currency> currenciesList = new ArrayList<>();
        try {for (String splitCurrency : splitCurrencies) {
                currenciesList.add(Currency.valueOf(splitCurrency));}
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return currenciesList;
    }
}