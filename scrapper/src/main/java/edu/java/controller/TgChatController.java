package edu.java.controller;

import dto.ApiErrorResponse;
import edu.java.apiException.AlreadyRegisteredException;
import edu.java.service.TgChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Validated
@Tag(name = "tg-chat", description = "the tg-chat API")
@RestController
@RequiredArgsConstructor
public class TgChatController {
    @Autowired
    private TgChatService tgChatService;

    /**
     * DELETE /tg-chat/{id} : Удалить чат
     *
     * @param id  (required)
     * @return Чат успешно удалён (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     *         or Чат не существует (status code 404)
     */
    @Operation(
        operationId = "tgChatIdDelete",
        summary = "Удалить чат",
        responses = {
            @ApiResponse(responseCode = "200", description = "Чат успешно удалён"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Чат не существует", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @DeleteMapping(
        value = "/tg-chat/{id}",
        produces = { "application/json" }
    )
    public ResponseEntity<Void> deleteTgChatId(
        @Parameter(name = "id", required = true, in = ParameterIn.PATH)
        @PathVariable("id")
        Long id) {
        this.tgChatService.unregister(id);

        return ResponseEntity.ok().build();
    }

    /**
     * POST /tg-chat/{id} : Зарегистрировать чат
     *
     * @param id  (required)
     * @return Чат зарегистрирован (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     */
    @Operation(
        operationId = "tgChatIdPost",
        summary = "Зарегистрировать чат",
        responses = {
            @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "Повторная регистрация", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @PostMapping(
        value = "/tg-chat/{id}",
        produces = { "application/json" }
    )
    public ResponseEntity<Void> postTgChatId(
        @Parameter(name = "id", required = true, in = ParameterIn.PATH)
        @PathVariable("id")
        Long id) throws AlreadyRegisteredException {
        this.tgChatService.register(id);

        return ResponseEntity.ok().build();
    }
}
