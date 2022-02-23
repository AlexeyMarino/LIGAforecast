package ru.liga.service;


import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс сервиса реализующий алгоритм среднеарифмитического рассчета курса
 */
public class AverageArithmeticService implements Service {

    private final RatesRepository repository;

    public AverageArithmeticService(RatesRepository repository) {
        this.repository = repository;
    }

    /**
     * Расчет курса валюты на завтра, на оснавании 7 предыдущих дней
     */
    @Override
    public Rate getDayRate(String currencyTitle) {
        List<Rate> rates = repository.getSevenDaysRates(currencyTitle);
        return getDayRateFromList(currencyTitle, rates, 1);
    }

    /**
     * Расчет курса валюты на неделю, день за днем, на основании 7 предыдущих дней
     */
    @Override
    public List<Rate> getWeekRate(String currencyTitle) {
        List<Rate> rates = repository.getSevenDaysRates(currencyTitle);
        for (int i = 0; i < rates.size(); i++) {
            Rate ratesDay = getDayRateFromList(currencyTitle, rates, i+1);
            rates.set(i, ratesDay);
        }
        return rates;
    }

    /**
     * Метод подсчитывающий среднее арифмитическое значение курса, общий для методов
     * отвечающих за предсказание на неделю и на день
     *
     * @param countDate счетчик дней необходимый для корректного указания даты во вновь сформированных объектах Rate
     *                  значение 1 - при прогнозе на 1 день, 7 - на неделю
     */
    public Rate getDayRateFromList(String currencyTitle, List<Rate> rates, int countDate) {
        Double rateTomorrow = rates.stream().mapToDouble(Rate::getRate).average().getAsDouble();
        return new Rate(LocalDate.now().plusDays(countDate), rateTomorrow, currencyTitle);
    }
}
