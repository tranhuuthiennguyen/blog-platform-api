package com.thiennth.blogplatformapi.model;

import jakarta.persistence.MappedSuperclass;

/**
 * Mixin for entities that are logically deleted (soft-delete pattern).
 *
 * <p>Hibernate {@code @Filter} named {@code "deletedFilter"} is applied automatically
 * when the filter is enabled on the Session. Repositories should activate it via:
 *
 * <pre>{@code
 * entityManager.unwrap(Session.class).enableFilter("deletedFilter").setParameter("isDeleted", false);
 * }</pre>
 *
 * <p>Spring Data JPA repositories can use {@code @Where} on the entity or rely on
 * the filter. The {@code @SQLDelete} override routes {@code DELETE} DML to an
 * {@code UPDATE} instead.
 *
 * <p><strong>Note:</strong> Unique constraints on columns must include {@code deleted_at IS NULL}
 * as a partial-index predicate in PostgreSQL (or equivalent) so that soft-deleted
 * rows do not block future re-use of the same natural key.
 */
@MappedSuperclass
public abstract class SoftDeletableEntity extends BaseEntity {
    
}
