package ru.liga.service;

import lombok.AllArgsConstructor;
import ru.liga.model.Currency;
import ru.liga.model.Period;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;

import java.util.List;

@AllArgsConstructor
public class ActualAlgorithmService implements ForecastService {

    private final RatesRepository repository;


    @Override
    public List<Rate> getRates(Currency currency, Period period) {
        return null;
    }
}
