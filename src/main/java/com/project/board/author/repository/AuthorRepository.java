package com.project.board.author.repository;

import com.project.board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author>findByEmail(String email);

//    jpql을 통해 raw 쿼리를 생성 : sql 과는 약간 다르다. (jpql만의 문법이 따로 있음)

    @Query("select distinct a from Author a left join fetch a.posts")
    List<Author> findAllFetchJoin();
}
