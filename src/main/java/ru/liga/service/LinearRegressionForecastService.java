package ru.liga.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;
import ru.liga.utils.LinearRegression;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class LinearRegressionForecastService implements ForecastService {
    @NonNull
    private final RatesRepository repository;
    private final int MONTH = 30;

    @Override
    public List<Rate> getRates(Currency currency, int period) {
        List<Rate> rates = repository.getRates(currency, MONTH);

        LinearRegression linearRegression = getLinearRegressionFromMonth(rates);

        LocalDate lastDate = rates.get(rates.size() - 1).getDate();
        int dateCounter = rates.size();
        while (!Objects.equals(lastDate, LocalDate.now())) {
            lastDate = lastDate.plusDays(1);
            dateCounter++;
        }
        List<Rate> result = new ArrayList<>();
        for (int i = dateCounter; i < dateCounter + period; i++) {
            result.add(new Rate(1, lastDate.plusDays(1), BigDecimal.valueOf(linearRegression.predict(i + 1)), currency));
            lastDate = lastDate.plusDays(1);
        }
        return result;
    }

    @Override
    public List<Rate> getDateRate(Currency currency, LocalDate date) {
        List<Rate> rates = repository.getRates(currency, MONTH);
        LinearRegression linearRegression = getLinearRegressionFromMonth(rates);
        LocalDate lastDate = rates.get(rates.size() - 1).getDate();
        int dateCounter = rates.size();

        while (!Objects.equals(lastDate, LocalDate.now())) {
            lastDate = lastDate.plusDays(1);
            dateCounter++;
        }
        List<Rate> result = new ArrayList<>();
        while (true) {
            lastDate = lastDate.plusDays(1);
            dateCounter++;
            if (lastDate.equals(date)) {
                result.add(new Rate(1, lastDate, BigDecimal.valueOf(linearRegression.predict(dateCounter)), currency));
                return result;
            }

        }
    }

    private LinearRegression getLinearRegressionFromMonth(List<Rate> rates) {
        double[] onlyRates = new double[rates.size()];
        for (int i = 0; i < rates.size(); i++) {
            onlyRates[i] = rates.get(i).getRate().doubleValue();
        }

        double[] days = new double[rates.size()];
        for (int i = 1; i <= rates.size(); i++) {
            days[i - 1] = i;
        }
        return new LinearRegression(days, onlyRates);
    }

}
