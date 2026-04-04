package com.thiennth.blogplatformapi.model.specification;

import java.time.LocalTime;
import java.time.ZoneOffset;

import org.springframework.data.jpa.domain.Specification;

import com.thiennth.blogplatformapi.dto.request.UserFilterRequest;
import com.thiennth.blogplatformapi.model.User;

public final class UserSpecification {
    
    private UserSpecification() {}

    public static Specification<User> from(UserFilterRequest filter) {
        return Specification
            .where(withCreatedFrom(filter))
            .and(withCreatedTo(filter));
    }

    // -------------------------------------------------------------------------
    // Individual predicates
    // -------------------------------------------------------------------------

    public static Specification<User> withCreatedFrom(UserFilterRequest filter) {
        return (root, query, cb) -> {
            if (filter.createdFrom() == null) return cb.conjunction();
            var from = filter.createdFrom().atStartOfDay().toInstant(ZoneOffset.UTC);
            return cb.greaterThanOrEqualTo(root.get("createdAt"), from);
        };
    }

    public static Specification<User> withCreatedTo(UserFilterRequest filter) {
        return (root, query, cb) -> {
            if (filter.createdTo() == null) return cb.conjunction();
            var to = filter.createdTo().atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC);
            return cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }

    public static <T> Specification<T> countSafe(Specification<T> spec) {
        return (root, query, cb) -> {
            if (Long.class.equals(query.getResultType())) {
                root.getFetches().clear();
                query.distinct(false);
            }
            return spec.toPredicate(root, query, cb);
        };
    }
}
