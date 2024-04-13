package edu.java.entity;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubRelease {
    private Long id;

    private Long repoId;

    private OffsetDateTime publishedAt;
}
