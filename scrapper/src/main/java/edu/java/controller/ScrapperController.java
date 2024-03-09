package edu.java.controller;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
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
    public ListLinkResponse getLinks(@RequestHeader("Tg-Chat-id") Long chatId) throws NotFoundException {
        return service.getLinks(chatId);
    }

    @PostMapping("/links")
    public LinkResponse addLink(@RequestHeader("Tg-Chat-id") Long chatId, @RequestBody AddLinkRequest request)
        throws BadRequestException, NotFoundException {
        LinkResponse link = new LinkResponse(chatId, request.link());

        return service.addLink(chatId, link);
    }

    @DeleteMapping("/links")
    public LinkResponse deleteLink(@RequestHeader("Tg-Chat-id") Long chatId, @RequestBody RemoveLinkRequest request)
        throws BadRequestException, NotFoundException {
        LinkResponse link = new LinkResponse(chatId, request.link());

        return service.deleteLink(chatId, link);
    }

    @PostMapping("/tg-chat/{id}")
    public void registerChat(@PathVariable("id") Long chatId) throws BadRequestException {
        service.registerChat(chatId);
    }

    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable("id") Long chatId) throws NotFoundException {
        service.deleteChat(chatId);
    }
}
