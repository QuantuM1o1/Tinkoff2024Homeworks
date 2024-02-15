package edu.java.bot.UserMessages;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Commands.Command;
import edu.java.bot.Commands.StartCommand;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private List<Command> commands;

    public UserMessageProcessorImpl() {
        // Initialize the list of commands
        this.commands = new ArrayList<>();
        // Add the /start command
        this.commands.add(new StartCommand());
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        SendMessage response = null;
        // Iterate through commands to find a match
        for (Command command : commands) {
            if (command.supports(update)) {
                response = command.handle(update);
                break;
            }
        }
        return response;
    }
}

