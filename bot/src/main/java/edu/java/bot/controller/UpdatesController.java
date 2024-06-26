package edu.java.bot.controller;

import dto.ApiErrorResponse;
import dto.LinkUpdateRequest;
import edu.java.bot.apiException.UserNotFoundException;
import edu.java.bot.service.UpdateNotifier;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Validated
@Tag(name = "updates", description = "the updates API")
@RestController
@RequiredArgsConstructor
public class UpdatesController {
    @Autowired
    private UpdateNotifier updateNotifier;

    /**
     * POST /updates : Отправить обновление
     *
     * @param linkUpdate  (required)
     * @return Обновление обработано (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     */
    @Operation(
        operationId = "updatesPost",
        summary = "Отправить обновление",
        responses = {
            @ApiResponse(responseCode = "200", description = "Обновление обработано"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @PostMapping(
        value = "/updates",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public ResponseEntity<Void> postUpdates(
        @Parameter(name = "LinkUpdateRequest", required = true)
        @Valid
        @RequestBody
        LinkUpdateRequest linkUpdate) throws UserNotFoundException {
        for (long id : linkUpdate.tgChatIds()) {
            this.updateNotifier.notifyUser(id, linkUpdate.description(), linkUpdate.url());
        }

        return ResponseEntity.ok().build();
    }
}
