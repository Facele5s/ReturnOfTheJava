package edu.java.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {
    @Id
    private Long id;

    @Column(name = "registration_date")
    private OffsetDateTime registrationDate;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "chat_link",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    Set<Link> links = new HashSet<>();

    public Chat(Long id, OffsetDateTime registrationDate) {
        this.id = id;
        this.registrationDate = registrationDate;
    }
}
