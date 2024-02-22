package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final List<Command> commands;
    private static final String commandName = "/help";
    private static final String commandDescription = "List all available commands";

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String name() {
        return commandName;
    }

    @Override
    public String description() {
        return commandDescription;
    }

    @Override
    public String handle(Update update) {
        StringBuilder messageText = new StringBuilder("Available commands:\n");
        for (Command command : this.commands) {
            messageText.append(command.name()).append(": ").append(command.description()).append("\n");
        }
        return messageText.toString();
    }
}
