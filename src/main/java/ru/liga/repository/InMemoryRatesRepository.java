package ru.liga.repository;

import ru.liga.model.Money;
import ru.liga.utils.ParseMoneyCsv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class InMemoryRatesRepository implements RatesRepository {

    static List<Money> usd = new ArrayList<>();
    static List<Money> euro = new ArrayList<>();
    static List<Money> turkey = new ArrayList<>();

    static {
        try {
            //хочется слить все в 1 репозиторий и фильтровать по валюте и чему угодно
            usd = ParseMoneyCsv.parse("src/main/resources/USD_F01_02_2002_T01_02_2022.csv");
            euro = ParseMoneyCsv.parse("src/main/resources/EUR_F01_02_2002_T01_02_2022.csv");
            turkey = ParseMoneyCsv.parse("src/main/resources/TRY_F01_02_2002_T01_02_2022.csv");
        } catch (IOException e) {
            System.out.println("Ошибка загрузки данных из файлов CSV"); // заменить методом Console
            e.printStackTrace();
        }
    }

    @Override
    public List<Money> getAll() {
        return null;
    }

    @Override
    public List<Money> getAll(String currencyTitle) {
        switch (currencyTitle.toLowerCase(Locale.ROOT)) {
            case "usd":
                return usd;
            case "euro":
                return euro;
            case "try":
                return turkey;
            default:
                return null;
        }
    }

    @Override
    public List<Money> getSevenLast(String currencyTitle) {
        List<Money> all = getAll(currencyTitle);
        all = all.stream().limit(7).collect(Collectors.toList());
        return all;
    }

    @Override
    public List<Money> getOneLast(String currencyTitle) {
        List<Money> all = getAll(currencyTitle);
        all = all.stream().limit(1).collect(Collectors.toList());
        return all;
    }

}
