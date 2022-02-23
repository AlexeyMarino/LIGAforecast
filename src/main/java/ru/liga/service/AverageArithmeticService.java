package ru.liga.service;


import ru.liga.model.Money;
import ru.liga.repository.InMemoryRatesRepository;
import ru.liga.repository.RatesRepository;

import java.util.List;

public class AverageArithmeticService implements Service {

    private final RatesRepository repository;

    public AverageArithmeticService(RatesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Money getDayRate(String currencyTitle) {
        List<Money> rates = repository.getSevenLast(currencyTitle);
        return getDayRateFromList(currencyTitle, rates);
    }

    public Money getDayRateFromList(String currencyTitle, List<Money> rates) {
        Double rateTomorrow = rates.stream().mapToDouble(Money::getRate).average().getAsDouble();
        return new Money(rates.get(0).getDate().plusDays(1), rateTomorrow, currencyTitle);
    }


    public List<Money> getWeekRate(String currencyTitle) {
        List<Money> rates = repository.getSevenLast(currencyTitle);
        for (int i = 0; i < rates.size(); i++) {
            Money ratesDay = getDayRateFromList(currencyTitle, rates);
            rates.set(i, ratesDay);
        }
        return rates;
    }
}
