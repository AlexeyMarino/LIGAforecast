package ru.liga.service;

import lombok.AllArgsConstructor;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class ActualAlgorithmService implements ForecastService {

    private final RatesRepository repository;


    public Rate getDayRate(Currency currency) {
        return null;
    }


    public List<Rate> getWeekRate(Currency currency) {
        return repository.getRates(currency, 7);
    }


    @Override
    public List<Rate> getRates(Currency currency, int Period) {
        return null;
    }

    @Override
    public List<Rate> getDateRate(Currency currency, LocalDate date) {
        return null;
    }
}
