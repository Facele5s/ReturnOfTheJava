package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.Command;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ObserverBot {
    private final TelegramBot telegramBot;

    private List<Command> commandsList;

    public void init() {
        addCommands();

        telegramBot.setUpdatesListener(updates -> {
            updates.stream().filter(update -> update.message() != null)
                .forEach(update -> {
                    try {
                        telegramBot.execute(parseCommand(update));
                    } catch (Exception e) {
                        log.error(e.toString());
                    }

                });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void addCommands() {
        BotCommand[] commands = commandsList.stream()
            .map(Command::convert)
            .toArray(BotCommand[]::new);

        telegramBot.execute(new SetMyCommands(commands));
    }

    public SendMessage parseCommand(Update update) {
        long chatId = update.message().chat().id();

        for (Command command: commandsList) {
            if (command.commandMatches(update)) {
                return command.respond(update);
            }
        }

        return new SendMessage(chatId, "The command is incorrect!");
    }

    public void sendMessageToChat(SendMessage message) {
        telegramBot.execute(message);
    }
}
