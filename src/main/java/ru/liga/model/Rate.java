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
    private String title;

    public Rate(LocalDate dateTime, BigDecimal rate, String title) {
        this.date = dateTime;
        this.rate = rate;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "date=" + date +
                ", rate=" + rate +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate1 = (Rate) o;
        return date.equals(rate1.date) && rate.equals(rate1.rate) && title.equals(rate1.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, rate, title);
    }
}
