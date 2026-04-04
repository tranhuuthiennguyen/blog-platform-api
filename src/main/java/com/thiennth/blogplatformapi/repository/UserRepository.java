package com.thiennth.blogplatformapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thiennth.blogplatformapi.model.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.firstName = ?1,
                u.lastName = ?2,
                u.bio = ?3,
                u.avatarUrl = ?4
            WHERE u.id = ?5
            """)
    void updateProfile(String firstName, String lastName, String bio, String avatarUrl, Long id);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.password = ?1
            WHERE u.id = ?2
            """)
    void updatePassword(String password, Long id);
}
