package ru.liga.utils;

import ru.liga.model.Money;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ParseMoneyCsv {

     public static List<Money> parse(String filePath) throws IOException {
        //Загружаем строки из файла
        List<Money> moneyList = new ArrayList<>();
        List<String> fileLines = Files.readAllLines(Paths.get(filePath));
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
            Money money = new Money();
            money.setDate(LocalDate.parse(columnList.get(0), DateTimeUtil.formatter));
            money.setRate(Double.parseDouble(columnList.get(1).replace(",", ".")));
            money.setTitle(columnList.get(2));
            moneyList.add(money);
        }
        return moneyList;
    }

    //Проверка является ли колонка частью предыдущей колонки
    private static boolean IsColumnPart(String text) {
        String trimText = text.trim();
        //Если в тексте одна ковычка и текст на нее заканчиваеться значит это часть предыдущей колонки
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }
}
