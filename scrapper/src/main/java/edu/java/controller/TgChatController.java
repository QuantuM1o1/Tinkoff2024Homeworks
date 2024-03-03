package edu.java.controller;

import edu.java.api.TgChatApi;
import edu.java.apiExceptions.AlreadyRegisteredException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@RestController
@RequiredArgsConstructor
public class TgChatController implements TgChatApi {
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return TgChatApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> tgChatIdDelete(Long id) {
        LOGGER.info("Deleted chat with id " + id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> tgChatIdPost(Long id) throws AlreadyRegisteredException {
        boolean alreadyRegistered = checkIfAlreadyRegistered(id);
        if (alreadyRegistered) {
            throw new AlreadyRegisteredException();
        }
        LOGGER.info("Added chat with id " + id);
        return ResponseEntity.ok().build();
    }

    private boolean checkIfAlreadyRegistered(Long id) {
        LOGGER.info("Checking for previous registration");
        return false;
    }
}
