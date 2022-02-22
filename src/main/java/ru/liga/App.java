package ru.liga;

import java.util.Scanner;

/**
 * !!!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("*currency forecast app*\n");
        boolean run = true;
        while (run) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("terminal=# ");
            String command = scanner.nextLine();
            if (command.equals("exit")) run = false;

        }
    }

}
