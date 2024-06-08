package edu.java.scrapper.domain.jooq;

import edu.java.entity.Chat;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.jooq.codegen.Tables.CHAT;
import static edu.java.scrapper.domain.jooq.codegen.Tables.CHAT_LINK;

@Repository
@RequiredArgsConstructor
public class JooqChatRepository {
    private final DSLContext dslContext;

    @Transactional
    public Chat add(Long chatId, OffsetDateTime registrationDate) {
        return dslContext
            .insertInto(CHAT, CHAT.ID, CHAT.REGISTRATION_DATE)
            .values(chatId, registrationDate)
            .returning(CHAT.fields())
            .fetchOneInto(Chat.class);
    }

    @Transactional
    public Chat remove(Long chatId) {
        return dslContext
            .deleteFrom(CHAT)
            .where(CHAT.ID.eq(chatId))
            .returning(CHAT.fields())
            .fetchOneInto(Chat.class);
    }

    @Transactional
    public Collection<Chat> findAll() {
        return dslContext
            .selectFrom(CHAT)
            .fetchInto(Chat.class);
    }

    @Transactional
    public Chat findById(Long chatId) {
        return dslContext
            .selectFrom(CHAT)
            .where(CHAT.ID.eq(chatId))
            .fetchOneInto(Chat.class);
    }

    @Transactional
    public Collection<Chat> findByLink(Long linkid) {
        var chats = dslContext.select(CHAT_LINK.CHAT_ID)
            .from(CHAT_LINK)
            .where(CHAT_LINK.LINK_ID.eq(linkid));

        return dslContext
            .selectFrom(CHAT)
            .where(CHAT.ID.in(chats))
            .fetchInto(Chat.class);
    }
}
