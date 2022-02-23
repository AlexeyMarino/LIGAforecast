package ru.liga.service;

import ru.liga.model.Money;

public interface Service {

    Money getDayRate(String currencyTitle);
}
