package com.major.mylog.repository;

import com.major.mylog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);
    List<Post> findByTitleOrText(String title, String text);

    Page<Post> findByTitleContainingOrTextContaining(String title, String text, Pageable pageable);
}
