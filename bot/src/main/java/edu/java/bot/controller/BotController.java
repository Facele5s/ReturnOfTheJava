package edu.java.bot.controller;

import edu.java.bot.service.BotService;
import edu.java.dto.exception.BadRequestException;
import edu.java.dto.request.LinkUpdateRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final BotService service;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The update has been processed"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping("/updates")
    public void postUpdates(@RequestBody LinkUpdateRequest request) throws BadRequestException {
        service.sendNotification(request);
    }
}
