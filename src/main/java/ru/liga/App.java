package ru.liga;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.controller.Controller;
import ru.liga.model.command.Command;
import ru.liga.repository.InMemoryRatesRepository;
import ru.liga.repository.RatesRepository;
import ru.liga.utils.CommandParser2;
import ru.liga.utils.ControllerFactory;
import ru.liga.view.TelegramView;


public class App {


    public static void main(String[] args) {
        Bot bot = new Bot();
        RatesRepository repository = new InMemoryRatesRepository();
        TelegramView view = new TelegramView(bot);
        CommandParser2 parser = new CommandParser2();
        bot.connectApi();

        while (true) {
            Message message = view.getMessage();
            Command command = parser.getCommand(message.getText());
            Long chatId = message.getChatId();
            if (command != null) {
                Controller controller = ControllerFactory.getController(command, repository);
                Object answer = controller.operate();
                view.printMessage(answer, chatId, command);
            }
        }
    }

}
