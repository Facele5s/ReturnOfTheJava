package edu.java.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.net.URI;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "link")
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private URI url;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "checked_at")
    private OffsetDateTime checkedAt;

    @ManyToOne
    @JoinColumn(name = "chat_id", unique = true, nullable = false)
    private ChatEntity chat;
}
