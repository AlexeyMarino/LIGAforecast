package ru.liga.view;

import java.util.Scanner;

public class Console {
    private final Scanner scanner;

    public Console() {
        scanner = new Scanner(System.in);
    }

    public String insertCommand() {
        System.out.print("Введите команду: ");
        return scanner.nextLine();
    }


}
