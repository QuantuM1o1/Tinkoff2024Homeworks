package edu.java.bot.userMessages;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UnknownCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.dto.ChatUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final List<Command> commands;
    private final Command unknownCommand;

    @Autowired
    public UserMessageProcessorImpl(Map<Long, ChatUser> usersMap) {
        this.commands = new ArrayList<>();
        unknownCommand = new UnknownCommand();
        this.commands.add(new StartCommand(usersMap));
        this.commands.add(new TrackCommand(usersMap));
        this.commands.add(new UntrackCommand(usersMap));
        this.commands.add(new ListCommand(usersMap));
        this.commands.add(new HelpCommand(this.commands));
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        SendMessage response = null;
        for (Command command : commands) {
            if (command.supports(update)) {
                response = new SendMessage(update.message().chat().id(), command.handle(update));
                break;
            }
        }
        if (response == null) {
            response = new SendMessage(update.message().chat().id(), unknownCommand.handle(update));
        }
        return response;
    }
}

