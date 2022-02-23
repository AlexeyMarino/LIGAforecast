package ru.liga.service;

import org.junit.jupiter.api.Test;
import ru.liga.model.Rate;
import ru.liga.repository.InMemoryRatesRepository;
import ru.liga.utils.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ServiceTest {

    Service service = new AverageArithmeticService(new InMemoryRatesRepository());


    @Test
    void getDayRate() {
        Rate actualRate = service.getDayRate("usd");
        Rate expectedRate = new Rate(LocalDate.parse("24.02.2022", DateTimeUtil.parseFormatter), 77.98224285714286, "usd");
        assertEquals(expectedRate, actualRate);
    }

    @Test
    void getWeekRate() {
        List<Rate> actualRates = service.getWeekRate("usd");
        Rate expectedRate1 = new Rate(LocalDate.parse("24.02.2022", DateTimeUtil.parseFormatter), 77.98224285714286, "usd");
        Rate expectedRate2 = new Rate(LocalDate.parse("02.03.2022", DateTimeUtil.parseFormatter), 78.03843869937576, "usd");
        assertEquals(expectedRate1, actualRates.get(0));
        assertEquals(expectedRate2, actualRates.get(6));
    }
}