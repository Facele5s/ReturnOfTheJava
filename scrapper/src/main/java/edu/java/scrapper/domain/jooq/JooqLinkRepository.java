package edu.java.scrapper.domain.jooq;

import edu.java.entity.Link;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.jooq.codegen.Tables.CHAT_LINK;
import static edu.java.scrapper.domain.jooq.codegen.Tables.LINK;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository {
    private final DSLContext dslContext;

    @Transactional
    public Link add(Long chatId, URI url) {
        Link link;

        Long linkCount = dslContext
            .selectCount()
            .from(LINK)
            .where(LINK.URL.eq(url.toString()))
            .fetchOneInto(Long.class);

        if (linkCount > 0) {
            link = dslContext
                .selectFrom(LINK)
                .where(LINK.URL.eq(url.toString()))
                .fetchOneInto(Link.class);
        } else {
            link = dslContext
                .insertInto(LINK, LINK.URL, LINK.UPDATED_AT, LINK.CHECKED_AT)
                .values(url.toString(), OffsetDateTime.now(), OffsetDateTime.now())
                .returning(LINK.fields())
                .fetchOneInto(Link.class);
        }

        dslContext
            .insertInto(CHAT_LINK, CHAT_LINK.CHAT_ID, CHAT_LINK.LINK_ID)
            .values(chatId, link.getId());

        return link;
    }

    @Transactional
    public Link remove(Long linkId) {
        return dslContext
            .deleteFrom(LINK)
            .where(LINK.ID.eq(linkId))
            .returning(LINK.fields())
            .fetchOneInto(Link.class);
    }

    @Transactional
    public Link untrack(Long chatId, URI url) {
        Link link = dslContext
            .selectFrom(LINK)
            .where(LINK.URL.eq(url.toString()))
            .fetchOneInto(Link.class);

        dslContext
            .deleteFrom(CHAT_LINK)
            .where(CHAT_LINK.CHAT_ID.eq(chatId).and(CHAT_LINK.LINK_ID.eq(link.getId())));

        Long chatsCount = dslContext
            .selectCount()
            .from(CHAT_LINK)
            .where(CHAT_LINK.LINK_ID.eq(link.getId()))
            .fetchOneInto(Long.class);

        if (chatsCount == 0) {
            dslContext
                .deleteFrom(LINK)
                .where(LINK.ID.eq(link.getId()));
        }

        return link;
    }

    @Transactional
    public Collection<Link> findAll() {
        return dslContext
            .selectFrom(LINK)
            .fetchInto(Link.class);
    }

    @Transactional
    public Link findById(Long linkId) {
        return dslContext
            .selectFrom(LINK)
            .where(LINK.ID.eq(linkId))
            .fetchOneInto(Link.class);
    }

    @Transactional
    public Collection<Link> findByChat(Long chatId) {
        var links = dslContext
            .select(CHAT_LINK.LINK_ID)
            .from(CHAT_LINK)
            .where(CHAT_LINK.CHAT_ID.eq(chatId));

        return dslContext
            .selectFrom(LINK)
            .where(LINK.ID.in(links))
            .fetchInto(Link.class);
    }

    @Transactional
    public Link findByUrl(URI url) {
        return dslContext
            .selectFrom(LINK)
            .where(LINK.URL.eq(url.toString()))
            .fetchOneInto(Link.class);
    }

    @Transactional
    public Collection<Link> findLongUnchecked(Duration duration) {
        OffsetDateTime dateTime = OffsetDateTime.now().minusSeconds(duration.getSeconds());

        return dslContext
            .selectFrom(LINK)
            .where(LINK.CHECKED_AT.lessThan(dateTime))
            .fetchInto(Link.class);
    }

    @Transactional
    public Link updateLink(Long linkId, OffsetDateTime updateTime) {
        return dslContext
            .update(LINK)
            .set(LINK.UPDATED_AT, updateTime)
            .where(LINK.ID.eq(linkId))
            .returning(LINK.fields())
            .fetchOneInto(Link.class);
    }

    @Transactional
    public Link checkLink(Long linkId, OffsetDateTime checkTime) {
        return dslContext
            .update(LINK)
            .set(LINK.CHECKED_AT, checkTime)
            .where(LINK.ID.eq(linkId))
            .returning(LINK.fields())
            .fetchOneInto(Link.class);
    }
}
