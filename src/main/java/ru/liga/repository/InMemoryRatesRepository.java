package ru.liga.repository;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.utils.ParseRateCsv;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Инмемори репозиторий для хранения значений курсов валют, загруженых из CSV файлов и работы с этими данными
 */

public class InMemoryRatesRepository implements RatesRepository {

    private static final Map<Currency, List<Rate>> repository = new HashMap<>();


    static {
        try {
            repository.put(Currency.USD, ParseRateCsv.parse("/USD_F01_02_2005_T05_03_2022.csv"));
            repository.put(Currency.EUR, ParseRateCsv.parse("/EUR_F01_02_2005_T05_03_2022.csv"));
            repository.put(Currency.TRY, ParseRateCsv.parse("/TRY_F01_02_2005_T05_03_2022.csv"));
            repository.put(Currency.AMD, ParseRateCsv.parse("/AMD_F01_02_2005_T05_03_2022.csv"));
            repository.put(Currency.BGN, ParseRateCsv.parse("/BGN_F01_02_2005_T05_03_2022.csv"));
        } catch (IOException e) {
            System.out.println("Ошибка загрузки данных из файлов CSV"); // заменить методом ConsoleView
            e.printStackTrace();
        }

    }

    @Override
    public List<Rate> getAllRates(Currency currency) {
        return repository.get(currency);
    }


    @Override
    public List<Rate> getRates(Currency currency, int numberOfDays) {
        return getAllRates(currency).stream()
                .limit(numberOfDays)
                .sorted(Comparator.comparing(Rate::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public void addRate(Rate rate, Currency currency) {
        List<Rate> all = getAllRates(currency);
        all.add(rate);
    }


}
