package edu.java.bot.controller;

import edu.java.bot.dto.LinkUpdate;
import edu.java.bot.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final BotService service;

    @PostMapping("/updates")
    public void postUpdates(@RequestBody LinkUpdate request) {
        service.sendNotification(request);
    }
}
