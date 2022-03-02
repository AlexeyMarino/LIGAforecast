package ru.liga;

import ru.liga.controller.Controller;
import ru.liga.repository.InMemoryRatesRepository;
import ru.liga.repository.RatesRepository;
import ru.liga.service.AverageArithmeticService;
import ru.liga.service.ForecastService;
import ru.liga.utils.ControllerSelection;
import ru.liga.view.Console;


/**
 *
 */
public class App {


    public static void main(String[] args) {
        RatesRepository repository = new InMemoryRatesRepository();
        ForecastService service = new AverageArithmeticService(repository);
        Console console = new Console();

        while (true) {
            String command = console.insertCommand();
            Controller controller = ControllerSelection.getController(command, service, console);
            controller.operate();
        }
    }

}
