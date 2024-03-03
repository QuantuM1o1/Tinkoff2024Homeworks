package edu.java.bot.controller;

import edu.java.bot.api.UpdatesApi;
import edu.java.bot.apiExceptions.UserNotFoundException;
import edu.java.bot.dto.ChatUser;
import dto.LinkUpdateRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@RestController
@RequiredArgsConstructor
public class UpdatesController implements UpdatesApi {
    @Autowired Map<Long, ChatUser> usersMap;
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return UpdatesApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> updatesPost(
        @Parameter(name = "LinkUpdateRequest", required = true) LinkUpdateRequest linkUpdate) throws UserNotFoundException {
        List<Long> checkAvailability = checkChats(linkUpdate.getTgChatIds());
        if (checkAvailability.isEmpty()) {
            LOGGER.info("All users are notified!");
        } else {
            for (Long chatId : checkAvailability) {
                LOGGER.info("Couldn't find user with id " + chatId);
            }
            throw new UserNotFoundException(checkAvailability);
        }

        return ResponseEntity.ok().build();
    }

    private List<Long> checkChats(List<Long> chatIds) {
        return chatIds.stream()
            .filter(chatId -> !usersMap.containsKey(chatId))
            .collect(Collectors.toList());
    }
}
