package ru.liga.utils;

import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
    public static DateTimeFormatter printFormatter = DateTimeFormatter.ofPattern("E dd.MM.yyyy");
}
