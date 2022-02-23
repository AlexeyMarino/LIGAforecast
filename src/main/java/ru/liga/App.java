package ru.liga;

import ru.liga.controller.Controller;
import ru.liga.repository.InMemoryRatesRepository;
import ru.liga.repository.RatesRepository;
import ru.liga.service.AverageArithmeticService;
import ru.liga.service.Service;
import ru.liga.utils.ControllerSelection;
import ru.liga.view.Console;


/**
 * !!!
 */
public class App {

    public static void main(String[] args) {
        RatesRepository repository = new InMemoryRatesRepository();
        Service service = new AverageArithmeticService(repository);
        Console console = new Console();
        System.out.println("*currency forecast app*\n");
        boolean run = true;
        while (run) {

            String command = console.insertCommand();
            if (command.equals("exit")) run = false;

            Controller controller = ControllerSelection.getController(command, service, console);
            controller.operate();
        }
    }

}
