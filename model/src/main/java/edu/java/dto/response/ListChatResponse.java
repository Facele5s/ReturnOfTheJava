package edu.java.dto.response;

import java.util.List;

public record ListChatResponse(
    List<ChatResponse> chats,
    Integer size
) {
}
