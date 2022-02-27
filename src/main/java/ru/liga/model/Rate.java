package ru.liga.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс описывоющий модель объекта Rate, хранящего информацию о курсе валюты в определенную дату
 */

public class Rate {
    private LocalDate date;
    private BigDecimal rate;
    private Currency currency;

    public Rate(LocalDate dateTime, BigDecimal rate, Currency currency) {
        this.date = dateTime;
        this.rate = rate;
        this.currency = currency;
    }

    public Rate() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "date=" + date +
                ", rate=" + rate +
                ", title='" + currency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate1 = (Rate) o;
        return date.equals(rate1.date) && rate.equals(rate1.rate) && currency.equals(rate1.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, rate, currency);
    }
}
