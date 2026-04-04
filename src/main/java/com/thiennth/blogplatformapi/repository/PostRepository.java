package com.thiennth.blogplatformapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiennth.blogplatformapi.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByAuthorIdAndStatus(Long authorId, Post.Status status);
}
