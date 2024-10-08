package edu.java.bot.userMessages;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.UnknownCommand;
import io.micrometer.core.instrument.Counter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMessageProcessorService implements UserMessageProcessor {
    @Autowired private List<Command> commands;
    @Autowired private Counter processedMessagesCounter;
    @Autowired private UnknownCommand unknownCommand;

    @Override
    public void process(Update update) {
        this.processedMessagesCounter.increment();
        boolean handled = false;
        for (Command command : this.commands) {
            if (command.supports(update)) {
                command.handle(update);
                handled = true;
                break;
            }
        }
        if (!handled) {
            this.unknownCommand.handle(update);
        }
    }
}

