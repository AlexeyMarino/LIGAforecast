package ru.liga.repository;

import ru.liga.model.Money;

import java.util.List;


public interface RatesRepository {
    List<Money> getAll();

    List<Money> getAll(String currencyTitle);

    List<Money> getSevenLast(String currencyTitle);

    List<Money> getOneLast(String currencyTitle);
}
