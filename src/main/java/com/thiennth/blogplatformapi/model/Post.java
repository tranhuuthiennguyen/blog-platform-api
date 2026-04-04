package com.thiennth.blogplatformapi.model;

import java.time.Instant;
import java.util.Objects;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

@Entity
@Getter
@Table(
    name = "posts",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_posts_slug",
        columnNames = {"slug"}
    )
)
public class Post extends BaseEntity {

    @Column(name = "author_id", updatable = false, nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 300)
    private String slug;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 500)
    private String summary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "status")
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    private Status status;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "published_at")
    private Instant publishedAt;

    // -------------------------------------------------------------------------
    // JPA no-arg constructor (package-private; not for application use)
    // -------------------------------------------------------------------------
    protected Post() {}

    // -------------------------------------------------------------------------
    // Static factory — the ONLY way to create an instance
    // -------------------------------------------------------------------------
    public static Post of(
        Long authorId,
        String title,
        String slug,
        String content,
        String summary,
        Status status,
        String coverImageUrl,
        Integer viewCount,
        Instant publishedAt
    ) {
        Objects.requireNonNull(authorId, "authorId must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(slug, "slug must not be null");
        Objects.requireNonNull(content, "content must not be null");
        Objects.requireNonNull(status, "status must not be null");

        if (viewCount < 0) {
            throw new IllegalArgumentException("viewCount must not be negative");
        }

        var entry = new Post();
        entry.authorId = authorId;
        entry. title = title;
        entry.slug = slug;
        entry.content = content;
        entry.summary = summary;
        entry.status = status;
        entry.coverImageUrl = coverImageUrl;
        entry.viewCount = viewCount;
        entry.publishedAt = publishedAt;
        return entry;
    }

    // -------------------------------------------------------------------------
    // Business key
    // -------------------------------------------------------------------------

    @Override
    protected Object domainKey() {
        return slug;
    }

    public enum Status {
        DRAFT,
        PUBLISHED,
        ARCHIVED;
    }
}
