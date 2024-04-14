package edu.java.repository;

import edu.java.entity.GithubRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGithubRepoRepository extends JpaRepository<GithubRepository, Long> {

}
