package ru.liga.repository;

import ru.liga.model.Rate;

import java.util.List;


public interface RatesRepository {

    List<Rate> getAll(String currencyTitle);

    List<Rate> getSevenDaysRates(String currencyTitle);

    List<Rate> getOneDayRate(String currencyTitle);
}
