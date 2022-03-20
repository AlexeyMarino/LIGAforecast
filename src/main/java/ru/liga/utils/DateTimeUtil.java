package ru.liga.utils;

import java.time.format.DateTimeFormatter;


/**
 * Класс содержащий инструменты для работы с парсингом и форматом времени и даты
 */
public class DateTimeUtil {
    public static final DateTimeFormatter PARSE_DATE_FORMATTER_DD_MM_YYYY = DateTimeFormatter.ofPattern("d.MM.yyyy");
    public static final DateTimeFormatter PRINT_DATE_FORMATTER_E_DD_MM_YYYY = DateTimeFormatter.ofPattern("E dd.MM.yyyy");
}
