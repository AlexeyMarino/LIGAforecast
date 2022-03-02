package ru.liga.utils;

import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс для парсинга СSV файлов и маппинга этих данных в модель
 */
public class ParseRateCsv {

    /**
     *
     * @param filePath путь к файлу для парсинга
     * @throws IOException в случае отсутствия файла или нарушении его структуры будет выброшено исключение
     */
    public static List<Rate> parse(String filePath) throws IOException {
        //Загружаем строки из файла
        List<String> fileLines;
        List<Rate> rateList = new ArrayList<>();
        try (InputStream in = ParseRateCsv.class.getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            fileLines = reader.lines().toList();
        }

        for (int i = 1; i < fileLines.size(); i++) {
            String fileLine = fileLines.get(i);
            String[] splitedText = fileLine.split(";");
            ArrayList<String> columnList = new ArrayList<>();
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
            Rate rate = new Rate();
            rate.setDate(LocalDate.parse(columnList.get(0), DateTimeUtil.PARSE_FORMATTER));
            rate.setRate(BigDecimal.valueOf(Double.parseDouble(columnList.get(1).replace(",", "."))));
            rate.setCurrency(getCurrency(columnList.get(2)));
            rateList.add(rate);
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
            default -> currency = null;
        }
        return currency;
    }
}
