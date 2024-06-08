package edu.java.scrapper.domain.jpa;

import edu.java.entity.GithubRepository;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGithubRepoRepository extends JpaRepository<GithubRepository, Long> {
    Collection<GithubRepository> findByUserName(String userName);

    GithubRepository findByName(String name);


}
