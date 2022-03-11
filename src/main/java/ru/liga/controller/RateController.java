package ru.liga.controller;

import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Period;
import ru.liga.model.Rate;
import ru.liga.model.command.Command;
import ru.liga.model.command.RateCommand;
import ru.liga.repository.RatesRepository;
import ru.liga.service.ActualAlgorithmService;
import ru.liga.service.ForecastService;
import ru.liga.service.LinearRegressionForecastService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс контроллера обрабатывающего запросы о прогнозе курсов валют
 */

public class RateController implements Controller {
    private final RateCommand command;
    private final RatesRepository repository;

    public RateController(Command command, RatesRepository repository) {
        this.command = (RateCommand) command;
        this.repository = repository;
    }

    @Override
    public Object operate() {
        ForecastService service = getService(command.getAlgorithm());

        if (command.getDate() != null) {
            return service.getDateRate(command.getCurrency().get(0), command.getDate());
        }

        if (command.getPeriod() != null) {
            if (command.getPeriod() == Period.MONTH) {
                return getRatesFromPeriod(service, 30);
            }
            if (command.getPeriod() == Period.WEEK) {
                return getRatesFromPeriod(service, 7);
            }
        }
        return "Неизвестная ошибка (";
    }


    private ForecastService getService(Algorithm algorithm) {
        if (algorithm == Algorithm.LR) {
            return new LinearRegressionForecastService(repository);
        }

        if (algorithm == Algorithm.ACTUAL) {
            return new ActualAlgorithmService(repository);
        }
        return new LinearRegressionForecastService(repository);
    }

    private Object getRatesFromPeriod(ForecastService service, int period) {
        Map<Currency, List<Rate>> ratesMap = new HashMap<>();
        if (command.getCurrency().size() == 1) {
            return ratesMap.put(command.getCurrency().get(0), service.getRates(command.getCurrency().get(0), period));
        }

        if (command.getCurrency().size() > 1) {
            for (int i = 0; i < command.getCurrency().size(); i++)
                ratesMap.put(command.getCurrency().get(i), service.getRates(command.getCurrency().get(i), period));
            return ratesMap;
        }
        return "Неизвестная ошибка (";
    }


}
