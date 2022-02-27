package ru.liga;

import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RateTestData {

    public static final Rate rate1 = new Rate(LocalDate.now(), new BigDecimal(10), Currency.EUR);
    public static final Rate rate2 = new Rate(LocalDate.now().minusDays(1), new BigDecimal(11), Currency.EUR);
    public static final Rate rate3 = new Rate(LocalDate.now().minusDays(2), new BigDecimal(12), Currency.EUR);
    public static final Rate rate4 = new Rate(LocalDate.now().minusDays(3), new BigDecimal(13), Currency.EUR);
    public static final Rate rate5 = new Rate(LocalDate.now().minusDays(4), new BigDecimal(14), Currency.EUR);
    public static final Rate rate6 = new Rate(LocalDate.now().minusDays(5), new BigDecimal(15), Currency.EUR);
    public static final Rate rate7 = new Rate(LocalDate.now().minusDays(6), new BigDecimal(16), Currency.EUR);

    public static final List<Rate> RATES = List.of(rate7, rate6, rate5, rate4, rate3, rate2, rate1);

    public static final Rate RATE = new Rate(LocalDate.now().plusDays(1), new BigDecimal(13), Currency.EUR);

}
