package ru.liga.utils;

import ru.liga.exception.IllegalOutputException;
import ru.liga.exception.InvalidCommandException;
import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Output;
import ru.liga.model.Period;
import ru.liga.model.command.Command;
import ru.liga.model.command.CommandName;
import ru.liga.validator.CommandValidator;

import java.util.List;
import java.util.Map;

import static ru.liga.exception.ExceptionMessage.ILLEGAL_GRAPH_OUTPUT;
import static ru.liga.exception.ExceptionMessage.ILLEGAL_LIST_OUTPUT;
import static ru.liga.model.command.CommandParameters.COMMAND_NAME;
import static ru.liga.model.command.CommandParameters.CURRENCIES;

public class CommandBuilder {
    CommandValidator validator = new CommandValidator();

    public Command buildCommand(Map<String, String> splitCommand) {
        CommandName commandName = validator.getValidateCommandName(splitCommand.get(COMMAND_NAME));

        if (commandName == CommandName.RATE) {
            List<Currency> currencies = validator.getValidateCurrencies(splitCommand.get(CURRENCIES));

            Period period = validator.getValidatePeriod(splitCommand);

            Algorithm algorithm = validator.getValidateAlgorithm(splitCommand);

            Output output = validator.getValidateOutput(splitCommand);


            if (currencies.size() > 1 && output.equals(Output.LIST))
                throw new IllegalOutputException(ILLEGAL_LIST_OUTPUT.getMessage());

            if (!period.isPeriod() && output.equals(Output.GRAPH))
                throw new InvalidCommandException(ILLEGAL_GRAPH_OUTPUT.getMessage());

            return new Command(commandName, currencies, period, algorithm, output);
        } else return new Command(commandName);

    }

}
