package edu.java.controller;

import edu.java.api.TgChatApi;
import edu.java.apiException.AlreadyRegisteredException;
import edu.java.service.TgChatService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@RestController
@RequiredArgsConstructor
public class TgChatController implements TgChatApi {
    @Autowired
    TgChatService tgChatService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return TgChatApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> tgChatIdDelete(Long id) {
        tgChatService.unregister(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> tgChatIdPost(Long id) throws AlreadyRegisteredException {
        boolean alreadyRegistered = checkIfAlreadyRegistered(id);
        if (alreadyRegistered) {
            throw new AlreadyRegisteredException();
        }
        tgChatService.register(id);
        return ResponseEntity.ok().build();
    }

    private boolean checkIfAlreadyRegistered(Long id) {
        return tgChatService.checkIfAlreadyRegistered(id);
    }
}
