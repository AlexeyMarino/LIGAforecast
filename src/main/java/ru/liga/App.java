package ru.liga;

import ru.liga.controller.Controller;
import ru.liga.utils.ControllerSelection;
import ru.liga.view.Console;


/**
 * !!!
 */
public class App {

    public static void main(String[] args) {

        Console console = new Console();
        System.out.println("*currency forecast app*\n");
        boolean run = true;
        while (run) {

            String command = console.insertCommand();
            if (command.equals("exit")) run = false;

            Controller controller = ControllerSelection.getController(command);
            controller.operate();
        }
    }

}
