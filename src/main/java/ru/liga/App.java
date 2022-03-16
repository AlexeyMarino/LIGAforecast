package ru.liga;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.controller.Controller;
import ru.liga.exception.BaseException;
import ru.liga.model.Answer;
import ru.liga.model.command.Command;
import ru.liga.repository.InMemoryRatesRepository;
import ru.liga.repository.RatesRepository;
import ru.liga.utils.CommandBuilder;
import ru.liga.utils.CommandParser;
import ru.liga.utils.ControllerFactory;
import ru.liga.view.TelegramView;


public class App {


    public static void main(String[] args) {
        Bot bot = new Bot();
        RatesRepository repository = new InMemoryRatesRepository();
        TelegramView view = new TelegramView(bot);
        CommandParser parser = new CommandParser();
        CommandBuilder builder = new CommandBuilder();
        bot.connectApi();

        while (true) {
            Message message = view.getMessage();
            Long chatId = message.getChatId();
            try {
                Command command = builder.buildCommand(parser.parse(message.getText()));
                Controller controller = ControllerFactory.getController(command, repository);
                Answer answer = controller.operate();
                view.printMessage(answer, chatId, command);
            } catch (BaseException e) {
                view.sendText(e.getMessage(), chatId);
            }
        }
    }

}
