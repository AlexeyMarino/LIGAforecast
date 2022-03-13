package ru.liga;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.controller.Controller;
import ru.liga.model.command.Command;
import ru.liga.repository.InMemoryRatesRepository;
import ru.liga.repository.RatesRepository;
import ru.liga.utils.CommandParser;
import ru.liga.utils.ControllerSelection;
import ru.liga.view.TelegramView;


public class App {


    public static void main(String[] args) {
        Bot bot = new Bot();
        RatesRepository repository = new InMemoryRatesRepository();
        TelegramView view = new TelegramView(bot);
        CommandParser parser = new CommandParser();
        bot.connectApi();

        while (true) {
            Message message = view.getMessage();
            Command command = parser.getCommand(message.getText());
            Long chatId = message.getChatId();
            Controller controller = ControllerSelection.getController(command, repository);
            Object answer = controller.operate();
            view.printMessage(answer, chatId, command);
        }
    }

}
