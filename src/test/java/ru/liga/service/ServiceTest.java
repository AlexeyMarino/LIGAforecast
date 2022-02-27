package ru.liga.service;

import org.junit.jupiter.api.Test;
import ru.liga.model.Rate;
import ru.liga.repository.InMemoryRatesRepository;
import ru.liga.utils.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ServiceTest {

    Service service = new AverageArithmeticService(new InMemoryRatesRepository());


    @Test
    void getDayRate() {

    }

    @Test
    void getWeekRate() {

    }
}