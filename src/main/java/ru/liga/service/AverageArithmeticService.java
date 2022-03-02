package ru.liga.service;


import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс сервиса реализующий алгоритм среднеарифмитического рассчета курса
 */
public class AverageArithmeticService implements ForecastService {

    private final RatesRepository repository;
    private final int DAY = 1;
    private final int WEEK = 7;

    public AverageArithmeticService(RatesRepository repository) {
        this.repository = repository;
    }

    /**
     * Расчет курса валюты на завтра, на оснавании 7 предыдущих дней
     */
    @Override
    public Rate getDayRate(Currency currency) {
        List<Rate> rates = repository.getSevenDaysRates(currency);
        supplementRates(currency, rates);
        return getDayRateFromList(currency, rates);
    }

    private void supplementRates(Currency currency, List<Rate> rates) {
        while (!Objects.equals(rates.get(rates.size()-1).getDate(), LocalDate.now())) {
            rates.add(getDayRateFromList(currency, rates));
        }
    }

    @Override
    public List<Rate> getWeekRate(Currency currency) {
        List<Rate> rates = repository.getSevenDaysRates(currency);
        supplementRates(currency, rates);
        List<Rate> forecastWeekRate = new ArrayList<>();
        for (int i = 0; i < 7 ; i++) {
            Rate ratesDay = getDayRateFromList(currency, rates);
            rates.add(ratesDay);
            forecastWeekRate.add(ratesDay);
        }
        return forecastWeekRate;
    }

    public Rate getDayRateFromList(Currency currency, List<Rate> rates) {
        List<BigDecimal> listRateFromSevenDays = rates
                .stream()
                .sorted(Comparator.comparing(Rate::getDate)
                        .reversed()).limit(7).map(Rate::getRate)
                .collect(Collectors.toList());

        return new Rate(rates.get(rates.size()-1).getDate().plusDays(1), average(listRateFromSevenDays, RoundingMode.FLOOR), currency);
    }

    private BigDecimal average(List<BigDecimal> bigDecimals, RoundingMode roundingMode) {
        BigDecimal sum = bigDecimals.stream()
                .map(Objects::requireNonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(bigDecimals.size()), roundingMode);
    }
}
