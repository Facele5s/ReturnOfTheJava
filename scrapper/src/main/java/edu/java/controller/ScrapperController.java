package edu.java.controller;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinkResponse;
import edu.java.service.ScrapperService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SuppressWarnings("LineLength")
public class ScrapperController {
    private final ScrapperService service;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Links have been successfully received"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "404", description = "The chat is not registered yet")
    })
    @GetMapping("/links")
    public ResponseEntity<ListLinkResponse> getLinks(@RequestHeader("Tg-Chat-id") Long chatId) throws NotFoundException {
        return new ResponseEntity<>(
            service.getLinks(chatId),
            HttpStatus.OK
        );
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The link was successfully added"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "404", description = "The chat is not registered yet")
    })
    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(
        @RequestHeader("Tg-Chat-id") Long chatId,
        @RequestBody AddLinkRequest request
    ) throws BadRequestException, NotFoundException {
        LinkResponse link = new LinkResponse(chatId, request.link());

        return new ResponseEntity<>(
            service.addLink(link),
            HttpStatus.OK
        );
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The link has been successfully removed"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "404", description = "The chat is not registered yet")
    })
    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> deleteLink(@RequestHeader("Tg-Chat-id") Long chatId, @RequestBody RemoveLinkRequest request)
        throws BadRequestException, NotFoundException {
        LinkResponse link = new LinkResponse(chatId, request.link());

        return new ResponseEntity<>(
            service.deleteLink(link),
            HttpStatus.OK
        );
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The chat is successfully registered"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable("id") Long chatId) throws BadRequestException {
        service.registerChat(chatId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The chat is successfully removed"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "404", description = "The chat is not registered yet")
    })
    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable("id") Long chatId) throws NotFoundException {
        service.deleteChat(chatId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
