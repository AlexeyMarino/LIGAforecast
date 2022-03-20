package ru.liga.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    INVALID_COMMAND("Неверный формат команды, воспользуйтесь командой Help"),
    ILLEGAL_ALGORITHM("Необхадимо выбрать алгоритм предсказания, воспользуйтесь командой 'help'"),
    ILLEGAL_OUTPUT("При выборе формы вывода прогноза необходимо указать 'list' или 'graph'"),
    ILLEGAL_PERIOD("После команды период необходимо указать 'month' или 'week'"),
    ILLEGAL_DATE("Дата прогноза введена в неправильном формате"),
    ILLEGAL_CURRENCY("Название вылюты указано в неправильном формате"),
    ILLEGAL_LIST_OUTPUT("Больше одного наименования валюты можно указывать только при выводе графика (-output graph)"),
    ILLEGAL_GRAPH_OUTPUT("Вывод данных в виде графика возможен только при предсказании на период, а не на конкретную дату"),
    ILLEGAL_DATE_FROM_ACTUAL("Введена слишком поздняя дата, последняя доступная для предсказания дата - "),
    REPEAT_COMMAND_PARAMETER("Вы ввели один параметр больше одного раза. Параметр: "),
    INTERNAL_ERROR("Произошла внутренняя ошибка приложения"),
    CSV_PARSE_ERROR("Ошибка загрузки данных из файлов CSV");


    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}
