package ru.liga.model;

import java.time.LocalDateTime;

public class Money {
    private LocalDateTime dateTime;
    private Double calories;
    private String title;

    public Money(LocalDateTime dateTime, Double calories, String title) {
        this.dateTime = dateTime;
        this.calories = calories;
        this.title = title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
