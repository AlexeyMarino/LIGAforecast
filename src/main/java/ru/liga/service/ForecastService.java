package ru.liga.service;

import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.time.LocalDate;
import java.util.List;

public interface ForecastService {

    List<Rate> getRates(Currency currency, int Period);

    List<Rate> getDateRate(Currency currency, LocalDate date);

}
