package edu.java.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class ChatEntity {
    @Id
    private Long id;

    @Column(name = "registration_date")
    private OffsetDateTime registrationDate;
}
