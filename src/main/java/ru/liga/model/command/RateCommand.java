package ru.liga.model.command;

import lombok.*;
import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Output;
import ru.liga.model.Period;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class RateCommand implements Command {
    private CommandName name;
    private List<Currency> currency;
    private LocalDate date;
    private Period period;
    private Algorithm algorithm;
    private Output output;

    public RateCommand(List<Currency> currency, LocalDate date, Period period, Algorithm algorithm, Output output) {
        this.name = CommandName.RATE;
        this.currency = currency;
        this.date = date;
        this.period = period;
        this.algorithm = algorithm;
        this.output = output;
    }

    public RateCommand() {
        this.name = CommandName.RATE;
    }

    @Override
    public CommandName getCommandName() {
        return name;
    }

    @Override
    public String getText() {
        return null;
    }
}
