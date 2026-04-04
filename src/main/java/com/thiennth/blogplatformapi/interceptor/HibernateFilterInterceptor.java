package com.thiennth.blogplatformapi.interceptor;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HibernateFilterInterceptor implements HandlerInterceptor {
    
    private final EntityManager entityManager;

    @Override
    public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deactivateFilter")
                .setParameter("isActive", true);
        return true;
    }
}
