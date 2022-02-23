package ru.liga.model;

import java.time.LocalDate;


public class Money {
    private LocalDate date;
    private Double rate;
    private String title;

    public Money(LocalDate dateTime, Double rate, String title) {
        this.date = dateTime;
        this.rate = rate;
        this.title = title;
    }

    public Money() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
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
        return "Money{" +
                "date=" + date +
                ", rate=" + rate +
                ", title='" + title + '\'' +
                '}';
    }
}
