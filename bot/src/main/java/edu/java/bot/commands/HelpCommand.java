package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HelpCommand implements Command {
    private final List<Command> commands;
    private static final String COMMAND_NAME = "/help";
    private static final String COMMAND_DESCRIPTION = "List all available commands";

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
    public String handle(Update update) {
        StringBuilder messageText = new StringBuilder("Available commands:\n");
        for (Command command : this.commands) {
            messageText.append(command.name()).append(": ").append(command.description()).append("\n");
        }
        return messageText.toString();
    }

    @Override
    public Command getInstance() {
        return new HelpCommand(commands);
    }
}
