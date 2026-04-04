package com.thiennth.blogplatformapi.dto.response;

import java.time.Instant;

import com.thiennth.blogplatformapi.model.Post;

public record PostResponse(
    Long id,
    String title,
    String slug,
    String content,
    String summary,
    String coverImageUrl,
    Integer viewCount,
    Instant publishedAt,
    Instant createdAt,
    Instant updatedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
            post.getId(), 
            post.getTitle(), 
            post.getSlug(), 
            post.getContent(), 
            post.getSummary(), 
            post.getCoverImageUrl(), 
            post.getViewCount(), 
            post.getPublishedAt(), 
            post.getCreatedAt(),
            post.getUpdatedAt());
    }
}
