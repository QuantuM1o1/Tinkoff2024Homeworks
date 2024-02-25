package edu.java.bot.userMessages;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMessageProcessorService implements UserMessageProcessor {
    @Autowired
    private List<Command> commands;

    @Override
    public List<? extends Command> commands() {
        return this.commands;
    }

    @Override
    public SendMessage process(Update update) {
        for (Command command : this.commands) {
            if (command.supports(update)) {
                return new SendMessage(update.message().chat().id(), command.handle(update));
            }
        }
        return new SendMessage(update.message().chat().id(), "Command is unknown");
    }
}

