package ru.liga.repository;

import java.util.Date;
import java.util.Map;

public interface RatesRepository {
    Map<Date, Double> getRate();
}
