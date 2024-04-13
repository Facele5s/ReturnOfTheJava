package edu.java.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubRepository {
    private Long id;

    private String userName;

    private String name;
}
