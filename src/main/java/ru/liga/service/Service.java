package ru.liga.service;

import ru.liga.model.Rate;

import java.util.List;

public interface Service {

    Rate getDayRate(String currencyTitle);

    List<Rate> getWeekRate(String currencyTitle);

}
