package edu.java.bot.controller;

import edu.java.bot.api.UpdatesApi;
import edu.java.bot.model.ApiErrorResponse;
import edu.java.bot.model.LinkUpdate;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UpdateController implements UpdatesApi {
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return UpdatesApi.super.getRequest();
    }

    @Override
    public ResponseEntity<ApiErrorResponse> updatesPost(LinkUpdate linkUpdate) {
        if (linkUpdate != null) {
            LOGGER.info("All users are notified!");
            return ResponseEntity.ok().build();
        } else {
            ApiErrorResponse response = new ApiErrorResponse();
            response.setCode("150");
//            return ResponseEntity.badRequest().body(response);
            return ResponseEntity.ok().build();
        }

    }
}
