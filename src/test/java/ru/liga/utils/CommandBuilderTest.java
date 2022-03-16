package ru.liga.utils;

import org.junit.jupiter.api.Test;
import ru.liga.exception.ExceptionMessage;
import ru.liga.exception.IllegalOutputException;
import ru.liga.exception.InvalidCommandException;
import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Output;
import ru.liga.model.Period;
import ru.liga.model.command.Command;
import ru.liga.model.command.CommandName;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandBuilderTest {
    CommandParser parser = new CommandParser();
    CommandBuilder builder = new CommandBuilder();

    //AAA - assign, action, assert;
    @Test
    void whenInputIsEmptyStringThenInvalidCommandException() {
        //assign
        String inputString = "";
        //action

        //assert
        assertThatThrownBy(() -> parser.parse(inputString))
                .isExactlyInstanceOf(InvalidCommandException.class)
                .hasMessage(ExceptionMessage.INVALID_COMMAND.getMessage());
    }

    @Test
    void whenInputIsCorrectThenValidCommand() {
        String inputString = "rate USD -period week -alg lr -output list";
        CommandName expectedCommandName = CommandName.RATE;
        List<Currency> expectedCurrencies = Collections.singletonList(Currency.USD);
        Period expectedPeriod = new Period();
        expectedPeriod.setPeriod(true);
        expectedPeriod.setDate(LocalDate.now().plusDays(7));
        Algorithm expectedAlgorithm = Algorithm.LR;
        Output expectedOutput = Output.LIST;

        Command command = builder.buildCommand(parser.parse(inputString));

        assertThat(command.getName()).isEqualTo(expectedCommandName);
        assertThat(command.getCurrency()).isEqualTo(expectedCurrencies);
        assertThat(command.getPeriod()).isEqualTo(expectedPeriod);
        assertThat(command.getAlgorithm()).isEqualTo(expectedAlgorithm);
        assertThat(command.getOutput()).isEqualTo(expectedOutput);
    }

    @Test
    void whenInputHasTwoCurrenciesAndNoGraphOutputThenIllegalOutputException() {
        String inputString = "rate USD,EUR -period week -alg lr -output list";

        assertThatThrownBy(() -> builder.buildCommand(parser.parse(inputString)))
                .isExactlyInstanceOf(IllegalOutputException.class)
                .hasMessage(ExceptionMessage.ILLEGAL_LIST_OUTPUT.getMessage());
    }

}