package edu.java.service;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.ChatResponse;
import edu.java.dto.response.ListChatResponse;

public interface ChatService {
    ChatResponse registerChat() throws BadRequestException;

    ChatResponse deleteChat(Long chatId) throws NotFoundException;

    ListChatResponse getAllChats();

    ChatResponse getChatById(Long chatId) throws NotFoundException;
}
