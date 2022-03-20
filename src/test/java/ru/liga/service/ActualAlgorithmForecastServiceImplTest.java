package ru.liga.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.liga.controller.Controller;
import ru.liga.model.Answer;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.model.command.Command;
import ru.liga.repository.RatesRepository;
import ru.liga.utils.CommandBuilder;
import ru.liga.utils.CommandParser;
import ru.liga.utils.ControllerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActualAlgorithmForecastServiceImplTest {
    private static final RatesRepository repository = mock(RatesRepository.class);

    @BeforeAll
    static void setRepositoryData() {
        List<Rate> rates = new ArrayList<>();
       for(int i = 1094; i >= 0; i--) {
           rates.add(new Rate(LocalDate.now().minusDays(i), new BigDecimal(100), Currency.USD));
       }
       when(repository.getRates(eq(Currency.USD), any(Integer.class))).thenAnswer(
           invocationOnMock -> rates.stream()
                   .limit(invocationOnMock.getArgumentAt(1, Integer.class))
                   .collect(Collectors.toList())
       );
    }

    @Test
    void whenTargetIsTomorrowThenRateIs200() {
        String commandString = "rate USD -date tomorrow -alg actual";
        Command command = new CommandBuilder().buildCommand(new CommandParser().parse(commandString));
        Controller controller = ControllerFactory.getController(command, repository);
        Answer operate = controller.operate();
        assertThat(operate.getRatesMap().get(Currency.USD).get(0).getRate().doubleValue()).isEqualTo(200d);
        assertThat(operate.getRatesMap().get(Currency.USD).get(0).getDate()).isEqualTo(LocalDate.now().plusDays(1));
    }
}