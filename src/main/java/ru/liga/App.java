package ru.liga;

public class App {


    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
            bot.connectApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


