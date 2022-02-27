package ru.liga.service;

import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.util.List;

public interface ForecastService {

    Rate getDayRate(Currency currency);

    List<Rate> getWeekRate(Currency currency);
}
