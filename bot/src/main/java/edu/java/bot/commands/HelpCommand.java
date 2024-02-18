package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "List all available commands";
    }

    @Override
    public String handle(Update update) {
        StringBuilder messageText = new StringBuilder("Available commands:\n");
        for (Command command : commands) {
            messageText.append(command.command()).append(": ").append(command.description()).append("\n");
        }
        return messageText.toString();
    }
}
