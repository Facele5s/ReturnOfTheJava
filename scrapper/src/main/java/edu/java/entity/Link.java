package edu.java.entity;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private URI url;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "checked_at")
    private OffsetDateTime checkedAt;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "chat_link",
        joinColumns = @JoinColumn(name = "link_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id")
    )
    Set<Chat> chats = new HashSet<>();

    public Link(Long id, URI url, OffsetDateTime updatedAt, OffsetDateTime checkedAt) {
        this.id = id;
        this.url = url;
        this.updatedAt = updatedAt;
        this.checkedAt = checkedAt;
    }
}
