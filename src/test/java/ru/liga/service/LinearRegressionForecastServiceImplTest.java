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
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LinearRegressionForecastServiceImplTest {
    private static final RatesRepository repository = mock(RatesRepository.class);

    @BeforeAll
    static void setRepositoryData() {
        List<Rate> rates = new ArrayList<>();
        for(int i = 29; i >= 0; i--) {
            rates.add(new Rate(LocalDate.now().minusDays(i), new BigDecimal(30-i), Currency.USD));
        }
        when(repository.getRates(Currency.USD, 30)).thenReturn(rates);
    }

    @Test
    void whenTargetIsTomorrowThenRateIs31() {
        String commandString = "rate USD -date tomorrow -alg lr";
        Command command = new CommandBuilder().build(new CommandParser().parse(commandString));
        Controller controller = ControllerFactory.getController(command, repository);
        Answer operate = controller.operate();
        assertThat(operate.getRatesMap().get(Currency.USD).get(0).getRate().doubleValue()).isEqualTo(31d);
    }

}