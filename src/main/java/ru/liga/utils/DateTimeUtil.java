package ru.liga.utils;

import java.time.format.DateTimeFormatter;


/**
 * Класс содержащий инструменты для работы с парсингом и форматом времени и даты
 */
public class DateTimeUtil {
    public static DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
    public static DateTimeFormatter printFormatter = DateTimeFormatter.ofPattern("E dd.MM.yyyy");
}
