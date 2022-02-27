package ru.liga.repository;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.utils.ParseRateCsv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Инмемори репозиторий для хранения значений курсов валют, загруженых из CSV файлов и работы с этими данными
 */

public class InMemoryRatesRepository implements RatesRepository {

    private static List<Rate> usd = new ArrayList<>();
    private static List<Rate> euro = new ArrayList<>();
    private static List<Rate> turkey = new ArrayList<>();

    static {
        try {
            usd = ParseRateCsv.parse("src/main/resources/USD_F01_02_2002_T01_02_2022.csv");
            euro = ParseRateCsv.parse("src/main/resources/EUR_F01_02_2002_T01_02_2022.csv");
            turkey = ParseRateCsv.parse("src/main/resources/TRY_F01_02_2002_T01_02_2022.csv");
        } catch (IOException e) {
            System.out.println("Ошибка загрузки данных из файлов CSV"); // заменить методом Console
            e.printStackTrace();
        }
    }

    /**
     * Метод возвращает курсы вылюты за весь период в зависимости от указанной валюты
     *
     * @param currency название валюты
     */
    @Override
    public List<Rate> getAllRates(Currency currency) {
        return switch (currency) {
            case USD -> usd;
            case EUR -> euro;
            case TRY -> turkey;
        };
    }

    /**
     * Метод возвращает курсы вылюты за последние 7 дней
     *
     * @param currency название валюты
     */

    @Override
    public List<Rate> getSevenDaysRates(Currency currency) {
        List<Rate> all = getAllRates(currency);
        all = all.stream().limit(7).sorted(Comparator.comparing(Rate::getDate)).collect(Collectors.toList());
        return all;
    }

    @Override
    public void addRate(Rate rate, Currency currency) {
        List<Rate> all = getAllRates(currency);
        all.add(rate);
    }


}
