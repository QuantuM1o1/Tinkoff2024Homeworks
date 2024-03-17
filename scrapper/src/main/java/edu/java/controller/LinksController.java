package edu.java.controller;

import dto.AddLinkRequest;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import edu.java.api.LinksApi;
import edu.java.apiException.LinkAlreadyExistsException;
import edu.java.dto.LinkDTO;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import edu.java.service.jdbc.JdbcLinkService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@RestController
@RequiredArgsConstructor
public class LinksController implements LinksApi {
    private final static Logger LOGGER = LogManager.getLogger();

    @Autowired
    JdbcLinkService linkService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return LinksApi.super.getRequest();
    }

    @Override
    public ResponseEntity<LinkResponse> linksDelete(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        linkService.remove(tgChatId, removeLinkRequest.getLink().toString());
        LinkResponse response = new LinkResponse()
            .id(tgChatId)
            .url(removeLinkRequest.getLink());
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<ListLinksResponse> linksGet(Long tgChatId) {
        Collection<LinkDTO> list = linkService.listAll(tgChatId);
        List<LinkResponse> responseList = new ArrayList<>();
        for (LinkDTO link : list) {
            LinkResponse linkResponse = new LinkResponse();
            linkResponse.setId(link.linkId());
            linkResponse.setUrl(URI.create(link.url()));
            responseList.add(linkResponse);
        }
        ListLinksResponse response = new ListLinksResponse();
        response.setLinks(responseList);
        response.setSize(responseList.size());
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<LinkResponse> linksPost(Long tgChatId, AddLinkRequest addLinkRequest)
        throws LinkAlreadyExistsException {
        boolean alreadyExists = checkIfLinkExists(tgChatId, addLinkRequest.getLink());
        if (alreadyExists) {
            throw new LinkAlreadyExistsException();
        }
        linkService.add(tgChatId, addLinkRequest.getLink().toString());
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
