package edu.java.controller;

import dto.AddLinkRequest;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import edu.java.api.LinksApi;
import edu.java.apiException.LinkAlreadyExistsException;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@RestController
@RequiredArgsConstructor
public class LinksController implements LinksApi {
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return LinksApi.super.getRequest();
    }

    @Override
    public ResponseEntity<LinkResponse> linksDelete(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        LinkResponse response = new LinkResponse()
            .id(tgChatId)
            .url(removeLinkRequest.getLink());
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<ListLinksResponse> linksGet(Long tgChatId) {
        ListLinksResponse response = new ListLinksResponse().size(0);
        LOGGER.info("List all links for user " + tgChatId);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<LinkResponse> linksPost(Long tgChatId, AddLinkRequest addLinkRequest)
        throws LinkAlreadyExistsException {
        boolean alreadyExists = checkIfLinkExists(tgChatId, addLinkRequest.getLink());
        if (alreadyExists) {
            throw new LinkAlreadyExistsException();
        }
        LinkResponse response = new LinkResponse()
            .id(tgChatId)
            .url(addLinkRequest.getLink());
        return ResponseEntity.ok().body(response);
    }

    private boolean checkIfLinkExists(Long id, URI url) {
        LOGGER.info("Checking if user already added this link before");
        return false;
    }
}
