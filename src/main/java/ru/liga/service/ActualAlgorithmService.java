package ru.liga.service;

import lombok.AllArgsConstructor;
import ru.liga.exception.ExceptionMessage;
import ru.liga.exception.IllegalDateException;
import ru.liga.model.Currency;
import ru.liga.model.Period;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ActualAlgorithmService implements ForecastService {
    private final RatesRepository repository;

    @Override
    public List<Rate> getRates(Currency currency, Period period) {
        int THREE_YEARS = 1095;
        List<Rate> rates = repository.getRates(currency, THREE_YEARS);
        if (period.getDate().isAfter(rates.get(rates.size() - 1).getDate().plusYears(2))) {
            throw new IllegalDateException(ExceptionMessage.ILLEGAL_DATE_FROM_ACTUAL.getMessage() + rates.get(rates.size() - 1).getDate());
        }
        List<Rate> resultRates = new ArrayList<>();
        if (period.isPeriod()) {
            LocalDate lastDate = LocalDate.now();
            while (!lastDate.equals(period.getDate())) {
                resultRates.add(getDateRate(rates, lastDate.plusDays(1), currency));
                lastDate = lastDate.plusDays(1);
            }
        } else {
            resultRates.add(getDateRate(rates, period.getDate(), currency));
        }
        return resultRates;
    }

    private Rate getDateRate(List<Rate> rates, LocalDate date, Currency currency) {

        BigDecimal dateRateOneYearBefore = rates.stream().filter(rate -> rate.getDate().equals(date.minusYears(1))).findFirst().get().getRate();
        BigDecimal dateRateTwoYearBefore = rates.stream().filter(rate -> rate.getDate().equals(date.minusYears(1))).findFirst().get().getRate();
        return new Rate(rates.get(rates.size() - 1).getNominal(), date, dateRateOneYearBefore.add(dateRateTwoYearBefore), currency);
    }
}
