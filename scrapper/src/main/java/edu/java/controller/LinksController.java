package edu.java.controller;

import dto.AddLinkRequest;
import dto.ApiErrorResponse;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import edu.java.apiException.LinkAlreadyExistsException;
import edu.java.dto.LinkDTO;
import edu.java.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "links", description = "the links controller")
public class LinksController {
    /**
     * DELETE /links : Убрать отслеживание ссылки
     *
     * @param tgChatId          (required)
     * @param removeLinkRequest (required)
     * @return Ссылка успешно убрана (status code 200)
     *     or Некорректные параметры запроса (status code 400)
     *     or Ссылка не найдена (status code 404)
     */
    @Operation(
        operationId = "linksDelete",
        summary = "Убрать отслеживание ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @DeleteMapping(
        value = "/links",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.OK)
    public LinkResponse deleteLinks(
        @NotNull
        @Parameter(
            name = "Tg-Chat-Id",
            required = true,
            in = ParameterIn.HEADER)
        @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
        @Parameter(
            name = "dto.RemoveLinkRequest",
            required = true)
        @Valid
        @RequestBody RemoveLinkRequest removeLinkRequest
    ) {
        return new LinkResponse(
            tgChatId,
            removeLinkRequest.link()
        );
    }

    /**
     * GET /links : Получить все отслеживаемые ссылки
     *
     * @param tgChatId (required)
     * @return Ссылки успешно получены (status code 200)
     *     or Некорректные параметры запроса (status code 400)
     */
    @Operation(
        operationId = "linksGet",
        summary = "Получить все отслеживаемые ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @GetMapping(
        value = "/links",
        produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.OK)
    public ListLinksResponse getLinks(
        @NotNull
        @Parameter(
            name = "Tg-Chat-Id",
            required = true,
            in = ParameterIn.HEADER)
        @RequestHeader(value = "Tg-Chat-Id")
        Long tgChatId
    ) {
        log.info("List all links for user " + tgChatId);
        return new ListLinksResponse(
            new ArrayList<>(),
            0
        );
    }

    /**
     * POST /links : Добавить отслеживание ссылки
     *
     * @param tgChatId       (required)
     * @param addLinkRequest (required)
     * @return Ссылка успешно добавлена (status code 200)
     *     or Некорректные параметры запроса (status code 400)
     */
    @Operation(
        operationId = "linksPost",
        summary = "Добавить отслеживание ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "Ссылка уже была добавлена ранее", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @PostMapping(
        value = "/links",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.OK)
    public LinkResponse postLinks(
        @NotNull
        @Parameter(
            name = "Tg-Chat-Id",
            required = true,
            in = ParameterIn.HEADER)
        @RequestHeader(value = "Tg-Chat-Id")
        Long tgChatId,
        @Parameter(
            name = "dto.AddLinkRequest",
            required = true)
        @Valid
        @RequestBody
        AddLinkRequest addLinkRequest
    ) throws LinkAlreadyExistsException {
        boolean alreadyExists = checkIfLinkExists(tgChatId, addLinkRequest.link());
        if (alreadyExists) {
            throw new LinkAlreadyExistsException();
        }
        return new LinkResponse(
            tgChatId,
            addLinkRequest.link()
        );
    }

    private boolean checkIfLinkExists(Long id, URI url) {
        log.info("Checking if user already added this link before");

        return false;
    }
}
