package ru.liga.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.liga.model.Currency;
import ru.liga.model.Period;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;
import ru.liga.utils.DateTimeConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class MysticAlgorithmForecastServiceImpl implements ForecastService {
    @NonNull
    private final RatesRepository repository;

    private final LocalDate FIRST_MOON_DATE = LocalDate.of(2021, 12, 19);
    private final LocalDate SECOND_MOON_DATE = LocalDate.of(2022, 1, 18);
    private final LocalDate THIRD_MOON_DATE = LocalDate.of(2022, 2, 16);

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
                resultRates.add(new Rate(lastDate, arithmeticMeanRateFromThreeMoon, currency));
                lastDate = lastDate.plusDays(1);
                arithmeticMeanRateFromThreeMoon = BigDecimal.valueOf(addPercentageToNumber(arithmeticMeanRateFromThreeMoon.doubleValue(), getPercentage(LOWER_BOUND, UPPER_BOUND)));
            }
        } else {
            resultRates.add(new Rate(period.getDate(), arithmeticMeanRateFromThreeMoon, currency));
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
        int THREE_DATES = 3;
        averageExchangeRateForThreeDays = averageExchangeRateForThreeDays.divide(BigDecimal.valueOf(THREE_DATES), RoundingMode.FLOOR);
        return averageExchangeRateForThreeDays;
    }

    private int getPercentage(int lowerBound, int upperBound) {
        Random random = new Random();
        return random.nextInt(upperBound) - lowerBound;
    }

    private Double addPercentageToNumber(double number, int percentage) {
        int FOR_PERCENT = 100;
        number += ((number * percentage) / FOR_PERCENT);
        return number;
    }

}
