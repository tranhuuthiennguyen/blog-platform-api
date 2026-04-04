package com.thiennth.blogplatformapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Root base entity for all persistent domain objects.
 *
 * <p>Provides:
 * <ul>
 *   <li>BigInt-based surrogate primary key (DB-generated for performance)</li>
 *   <li>Full Spring Data JPA auditing (created/updated timestamps + principals)</li>
 *   <li>Semantic equals/hashCode based on business identity (not DB identity)</li>
 *   <li>Defensive toString (no lazy collections, no sensitive fields)</li>
 * </ul>
 *
 * <p><strong>Usage:</strong> Extend this class for every JPA entity. Override
 * {@link #domainKey()} to provide a stable, human-readable business key used in
 * equals/hashCode so that detached and persisted instances compare correctly.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    // -------------------------------------------------------------------------
    // Identity
    // -------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    // -------------------------------------------------------------------------
    // Auditing
    // -------------------------------------------------------------------------

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // -------------------------------------------------------------------------
    // Accessors (read-only by design — IDs are never mutated)
    // -------------------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    // -------------------------------------------------------------------------
    // Business identity — subclasses MUST override this
    // -------------------------------------------------------------------------
 
    /**
     * Returns a stable, non-null business key unique within the entity type.
     * Used for equals/hashCode so that transient, detached, and managed
     * instances behave correctly in Sets and Maps.
     *
     * <p>Example: an {@code Email} entity returns its lower-cased address string.
     */
    protected abstract Object domainKey();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof BaseEntity other)) return false;
        if (!this.getClass().equals(other.getClass())) return false;
        Object key = domainKey();
        return key != null && key.equals(other.domainKey());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(domainKey());
    }

    // -------------------------------------------------------------------------
    // toString — safe; never traverses associations
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return "%s{id=%s, domainKey=%s}"
            .formatted(getClass().getSimpleName(), id, domainKey());
    }
}
