package ru.liga.utils;

import java.time.format.DateTimeFormatter;


/**
 * Класс содержащий инструменты для работы с парсингом и форматом времени и даты
 */
public class DateTimeUtil {
    public static final DateTimeFormatter PARSE_FORMATTER = DateTimeFormatter.ofPattern("d.MM.yyyy");
    public static final DateTimeFormatter PRINT_FORMATTER = DateTimeFormatter.ofPattern("E dd.MM.yyyy");
}
