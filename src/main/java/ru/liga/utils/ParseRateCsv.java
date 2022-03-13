package ru.liga.utils;

import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


/**
 * Класс для парсинга СSV файлов и маппинга этих данных в модель
 */
public class ParseRateCsv {

    /**
     * @param filePath путь к файлу для парсинга
     * @throws IOException в случае отсутствия файла или нарушении его структуры будет выброшено исключение
     */
    public static List<Rate> parse(String filePath) throws IOException {
        //Загружаем строки из файла
        List<String> fileLines;
        List<Rate> rateList = new ArrayList<>();
        try (InputStream in = ParseRateCsv.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)))) {
            fileLines = reader.lines().toList();
        }
        LocalDate lastDate = null;
        for (int i = 1; i < fileLines.size(); i++) {
            String fileLine = fileLines.get(i);
            String[] splitedText = fileLine.split(";");
            List<String> columnList = new ArrayList<>();
            for (String s : splitedText) {
                //Если колонка начинается на кавычки или заканчиваеться на кавычки
                if (IsColumnPart(s)) {
                    String lastText = columnList.get(columnList.size() - 1);
                    columnList.set(columnList.size() - 1, lastText + "," + s);
                } else {
                    columnList.add(s);
                }
            }

            //Создаем сущности на основе полученной информации
            int nominal = (int) Double.parseDouble(columnList.get(0));
            LocalDate currentDate = LocalDate.parse(columnList.get(1), DateTimeUtil.PARSE_FORMATTER);
            BigDecimal currentRate = null;
            try {
                currentRate = BigDecimal.valueOf(NumberFormat.getInstance(new Locale("RU")).parse(columnList.get(2).replace("\"", "")).doubleValue());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Currency currency = getCurrency(columnList.get(3));
            while (lastDate != null && !currentDate.equals(lastDate.minusDays(1))) {
                lastDate = lastDate.minusDays(1);
                Rate rate = new Rate(nominal, lastDate, currentRate, currency);
                rateList.add(rate);
            }
            rateList.add(new Rate(nominal, currentDate, currentRate, currency));
            lastDate = currentDate;
        }
        return rateList;
    }

    //Проверка является ли колонка частью предыдущей колонки
    private static boolean IsColumnPart(String text) {
        String trimText = text.trim();
        //Если в тексте одна ковычка и текст на нее заканчиваеться значит это часть предыдущей колонки
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }

    private static Currency getCurrency(String currencyTitle) {
        Currency currency;
        switch (currencyTitle) {
            case "Доллар США" -> currency = Currency.USD;
            case "ЕВРО" -> currency = Currency.EUR;
            case "Турецкая лира" -> currency = Currency.TRY;
            case "Армянский драм" -> currency = Currency.AMD;
            case "Болгарский лев" -> currency = Currency.BGN;
            default -> currency = null;
        }
        return currency;
    }


}
