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
    public ListLinkResponse getLinks(@RequestHeader("Tg-Chat-id") Long chatId) {
        return service.getLinks(chatId);
    }

    @PostMapping("/links")
    public LinkResponse addLink(@RequestHeader("Tg-Chat-id") Long chatId, @RequestBody AddLinkRequest request) {
        LinkResponse link = new LinkResponse(chatId, request.link());

        return service.addLink(chatId, link);
    }

    @DeleteMapping("/links")
    public LinkResponse deleteLink(@RequestHeader("Tg-Chat-id") Long chatId, @RequestBody RemoveLinkRequest request) {
        LinkResponse link = new LinkResponse(chatId, request.link());

        return service.deleteLink(chatId, link);
    }

    @PostMapping("/tg-chat/{id}")
    public void registerChat(@PathVariable("id") Long chatId) {
        service.registerChat(chatId);
    }

    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable("id") Long chatId) {
        service.deleteChat(chatId);
    }
}
