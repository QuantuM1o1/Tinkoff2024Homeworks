package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.TelegramBotWriterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelpCommand implements Command {
    private static final String COMMAND_NAME = "/help";
    private static final String COMMAND_DESCRIPTION = "List all available commands";
    private final List<Command> commands;
    @Autowired private TelegramBotWriterService botWriterService;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String name() {
        return COMMAND_NAME;
    }

    @Override
    public String description() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public void handle(Update update) {
        StringBuilder message = new StringBuilder("Available commands:\n");
        for (Command command : this.commands) {
            message.append(command.name()).append(": ").append(command.description()).append("\n");
        }

        this.botWriterService.sendMessage(update.message().chat().id(), message.toString());
    }
}
