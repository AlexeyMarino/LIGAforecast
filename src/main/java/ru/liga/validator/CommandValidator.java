package ru.liga.validator;

import ru.liga.exception.*;
import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Output;
import ru.liga.model.Period;
import ru.liga.model.command.CommandName;
import ru.liga.utils.DateTimeUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ru.liga.exception.ExceptionMessage.*;
import static ru.liga.model.command.CommandParameters.*;

public class CommandValidator {
    public Output getValidateOutput(Map<String, String> splitCommand) {
        Output output;
        if (splitCommand.containsKey(OUTPUT)) {
            try {
                output = Output.valueOf(splitCommand.get(OUTPUT).toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalOutputException(ILLEGAL_OUTPUT.getMessage());
            }
        } else {
            output = Output.LIST;
        }
        return output;
    }

    public Algorithm getValidateAlgorithm(Map<String, String> splitCommand) {
        Algorithm algorithm;
        try {
            algorithm = Algorithm.valueOf(splitCommand.get(ALGORITHM).toUpperCase());
        } catch (Exception e) {
            throw new IllegalAlgorithmException(ILLEGAL_ALGORITHM.getMessage());
        }
        return algorithm;
    }

    public CommandName getValidateCommandName(String name) {
        CommandName commandName;
        try {
            commandName = CommandName.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException(INVALID_COMMAND.getMessage());
        }
        return commandName;
    }

    public List<Currency> getValidateCurrencies(String currenciesString) {
        List<String> splitCurrencies = Arrays.stream(currenciesString.split(",")).distinct().toList();
        List<Currency> currencies = new ArrayList<>();

        try {
            for (String splitCurrency : splitCurrencies) {
                currencies.add(Currency.valueOf(splitCurrency.toUpperCase()));
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalCurrencyTitleException(ILLEGAL_CURRENCY.getMessage());
        }
        return currencies;
    }

    public Period getValidatePeriod(Map<String, String> splitCommand) {
        Period period = new Period();

        if (splitCommand.containsKey(PERIOD) && splitCommand.containsKey(DATE))
            throw new InvalidCommandException(INVALID_COMMAND.getMessage());

        if (splitCommand.containsKey(PERIOD)) {
            switch (splitCommand.get(PERIOD).toLowerCase()) {
                case MONTH -> {
                    period.setDate(LocalDate.now().plusDays(30));
                    period.setPeriod(true);
                }
                case WEEK -> {
                    period.setDate(LocalDate.now().plusDays(7));
                    period.setPeriod(true);
                }
                default -> throw new IllegalPeriodException(ILLEGAL_PERIOD.getMessage());
            }
        } else if (splitCommand.containsKey(DATE)) {
            if (splitCommand.get(DATE).equalsIgnoreCase(TOMORROW)) {
                period.setDate(LocalDate.now().plusDays(1));
                period.setPeriod(false);
            } else {
                try {
                    period.setDate(LocalDate.parse(splitCommand.get(DATE), DateTimeUtil.PARSE_DATE_FORMATTER_DD_MM_YYYY));
                    period.setPeriod(false);
                } catch (Exception e) {
                    throw new IllegalDateException(ILLEGAL_DATE.getMessage());
                }
            }
        } else {
            throw new InvalidCommandException(INVALID_COMMAND.getMessage());
        }
        return period;
    }
}
