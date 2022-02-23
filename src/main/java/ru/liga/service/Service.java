package ru.liga.service;

import ru.liga.model.Money;

import java.util.List;

public interface Service {

    Money getDayRate(String currencyTitle);

    List<Money> getWeekRate(String currencyTitle);
}
