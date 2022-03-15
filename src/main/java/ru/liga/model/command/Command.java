package ru.liga.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Output;
import ru.liga.model.Period;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Command {
    private CommandName name;
    private List<Currency> currency;
    private Period period;
    private Algorithm algorithm;
    private Output output;

    public Command(CommandName name) {
        this.name = name;
    }
}
