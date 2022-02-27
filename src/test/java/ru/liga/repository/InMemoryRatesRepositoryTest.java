package ru.liga.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.RateTestData;
import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.liga.RateTestData.RATES;
import static ru.liga.RateTestData.rate1;

class InMemoryRatesRepositoryTest {

    RatesRepository repository = new InMemoryRatesRepository();

    @BeforeEach
    void setUp() {
        repository.getAllRates(Currency.EUR).addAll(0, RATES);
    }

    @Test
    void getSevenDaysRates() {
        List<Rate> actual = repository.getSevenDaysRates(Currency.EUR);
        assertThat(actual).isEqualTo(RATES);
    }

    @Test
    void addRate() {
        repository.addRate(RateTestData.rate1, Currency.EUR);
        List<Rate> rates = repository.getAllRates(Currency.EUR);
        Rate actual = rates.get(rates.size() - 1);
        assertThat(actual).isEqualTo(rate1);
    }
}