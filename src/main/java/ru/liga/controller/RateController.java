package ru.liga.controller;

import ru.liga.model.*;
import ru.liga.model.command.Command;
import ru.liga.repository.RatesRepository;
import ru.liga.service.ActualAlgorithmService;
import ru.liga.service.ForecastService;
import ru.liga.service.LinearRegressionForecastService;
import ru.liga.service.MysticAlgorithmService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс контроллера обрабатывающего запросы о прогнозе курсов валют
 */

public class RateController implements Controller {
    private final Command command;
    private final RatesRepository repository;

    public RateController(Command command, RatesRepository repository) {
        this.command = command;
        this.repository = repository;
    }

    @Override
    public Answer operate() {
        ForecastService service = getService(command.getAlgorithm());
        boolean output = command.getOutput() == Output.GRAPH;
        return new Answer(getRatesFromPeriod(service, command.getPeriod()), output);
    }

    private ForecastService getService(Algorithm algorithm) {
        if (algorithm == Algorithm.LR) {
            return new LinearRegressionForecastService(repository);
        } else if (algorithm == Algorithm.ACTUAL) {
            return new ActualAlgorithmService(repository);
        } else return new MysticAlgorithmService(repository);
    }

    private Map<Currency, List<Rate>> getRatesFromPeriod(ForecastService service, Period period) {
        Map<Currency, List<Rate>> ratesMap = new HashMap<>();
        for (int i = 0; i < command.getCurrency().size(); i++)
            ratesMap.put(command.getCurrency().get(i), service.getRates(command.getCurrency().get(i), period));
        return ratesMap;
    }


}
