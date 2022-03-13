package ru.liga.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Класс описывоющий модель объекта Rate, хранящего информацию о курсе валюты в определенную дату
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Rate {
    private int nominal;
    private LocalDate date;
    private BigDecimal rate;
    private Currency currency;
}
