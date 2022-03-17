package ru.liga.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.liga.model.Currency;
import ru.liga.model.Period;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;
import ru.liga.utils.DateTimeUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class MysticAlgorithmService implements ForecastService {
    @NonNull
    private final RatesRepository repository;

    private final LocalDate FIRST_MOON_DATE = LocalDate.parse("19.12.2021", DateTimeUtil.PARSE_FORMATTER);
    private final LocalDate SECOND_MOON_DATE = LocalDate.parse("18.01.2022", DateTimeUtil.PARSE_FORMATTER);
    private final LocalDate THIRD_MOON_DATE = LocalDate.parse("16.02.2022", DateTimeUtil.PARSE_FORMATTER);

    @Override
    public List<Rate> getRates(Currency currency, Period period) {
        int THREE_MONTH = 90;
        int LOWER_BOUND = 10;
        int UPPER_BOUND = 20;
        List<Rate> rates = repository.getRates(currency, THREE_MONTH);
        List<LocalDate> moonsDates = new ArrayList<>();
        Collections.addAll(moonsDates, FIRST_MOON_DATE, SECOND_MOON_DATE, THIRD_MOON_DATE);

        List<Rate> resultRates = new ArrayList<>();

        BigDecimal arithmeticMeanRateFromThreeMoon = getArithmeticMeanRateFromThreeMoon(moonsDates, rates);


        if (period.isPeriod()) {
            LocalDate lastDate = LocalDate.now();
            while (!lastDate.equals(period.getDate())) {
                resultRates.add(new Rate(rates.get(rates.size() - 1).getNominal(), lastDate, arithmeticMeanRateFromThreeMoon, currency));
                lastDate = lastDate.plusDays(1);
                arithmeticMeanRateFromThreeMoon = BigDecimal.valueOf(addPercentageToNumber(arithmeticMeanRateFromThreeMoon.doubleValue(), getPercentage(LOWER_BOUND, UPPER_BOUND)));
            }
        } else {
            resultRates.add(new Rate(rates.get(rates.size() - 1).getNominal(), period.getDate(), arithmeticMeanRateFromThreeMoon, currency));
        }
        return resultRates;
    }


    private BigDecimal getArithmeticMeanRateFromThreeMoon(List<LocalDate> moonsDates, List<Rate> rates) {
        BigDecimal averageExchangeRateForThreeDays = new BigDecimal(0);
        for (int i = 0; i < moonsDates.size(); i++) {
            int finalI = i;
            averageExchangeRateForThreeDays = averageExchangeRateForThreeDays.add(rates.stream()
                    .filter(rate -> rate.getDate().equals(moonsDates.get(finalI)))
                    .findFirst()
                    .get()
                    .getRate());
        }
        averageExchangeRateForThreeDays = averageExchangeRateForThreeDays.divide(BigDecimal.valueOf(3), RoundingMode.FLOOR);
        return averageExchangeRateForThreeDays;
    }

    private int getPercentage(int lowerBound, int upperBound) {
        Random random = new Random();
        return random.nextInt(upperBound) - lowerBound;
    }

    private Double addPercentageToNumber(double number, int percentage) {
        number = number + ((number * percentage) / 100);
        return number;
    }

}
