package edu.java.entity;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Link {
    private Long id;
    private Long chatId;
    private URI url;
    private OffsetDateTime updatedAt;
    private OffsetDateTime checkedAt;
}
