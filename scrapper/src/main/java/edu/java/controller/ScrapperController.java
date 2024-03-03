package edu.java.controller;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinkResponse;
import edu.java.dto.RemoveLinkRequest;
import edu.java.service.ScrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScrapperController {
    private final ScrapperService service;

    @GetMapping("/links")
    public ListLinkResponse getLinks(@RequestHeader("Tg-chat-id") Long id) {
        return service.getLinks(id);
    }

    @PostMapping("/links")
    public LinkResponse addLink(@RequestHeader("Tg-chat-id") Long id, @RequestBody AddLinkRequest request) {
        LinkResponse link = new LinkResponse(id, request.link());

        return service.addLink(id, link);
    }

    @DeleteMapping("/links")
    public LinkResponse deleteLink(@RequestHeader("Tg-chat-id") Long id, @RequestBody RemoveLinkRequest request) {
        LinkResponse link = new LinkResponse(id, request.link());

        return service.deleteLink(id, link);
    }

    @PostMapping("/tg-chat/{id}")
    public void registerChat(@PathVariable("id") Long id) {
        service.registerChat(id);
    }

    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable("id") Long id) {
        service.deleteChat(id);
    }
}
