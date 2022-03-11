package ru.liga.model.command;

import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Output;
import ru.liga.model.Period;

import java.time.LocalDate;
import java.util.List;

public class RateCommand implements Command {
    private final CommandName name;
    private final List<Currency> currency;
    private final LocalDate date;
    private final Period period;
    private final Algorithm algorithm;
    private final Output output;


    public RateCommand(List<Currency> currency, LocalDate date, Period period, Algorithm algorithm, Output output) {
        this.name = CommandName.RATE;
        this.currency = currency;
        this.date = date;
        this.period = period;
        this.algorithm = algorithm;
        this.output = output;
    }

    @Override
    public CommandName getCommandName() {
        return name;
    }

    @Override
    public String getText() {
        return null;
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public Period getPeriod() {
        return period;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Output getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "RateCommand{" +
                "name=" + name +
                ", currency=" + currency +
                ", date=" + date +
                ", period=" + period +
                ", algorithm=" + algorithm +
                ", output=" + output +
                '}';
    }
}
