package com.thiennth.blogplatformapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thiennth.blogplatformapi.dto.PaginatedResponse;
import com.thiennth.blogplatformapi.dto.request.ChangePasswordRequest;
import com.thiennth.blogplatformapi.dto.request.UpdateUserProfileRequest;
import com.thiennth.blogplatformapi.dto.request.UserFilterRequest;
import com.thiennth.blogplatformapi.dto.response.PostResponse;
import com.thiennth.blogplatformapi.dto.response.PublishedPostsListByAuthorResponse;
import com.thiennth.blogplatformapi.dto.response.UserResponse;
import com.thiennth.blogplatformapi.exception.BadRequestException;
import com.thiennth.blogplatformapi.exception.ForBiddenActionException;
import com.thiennth.blogplatformapi.exception.UserNotFoundException;
import com.thiennth.blogplatformapi.model.Post;
import com.thiennth.blogplatformapi.model.User;
import com.thiennth.blogplatformapi.model.specification.UserSpecification;
import com.thiennth.blogplatformapi.repository.PostRepository;
import com.thiennth.blogplatformapi.repository.UserRepository;
import com.thiennth.blogplatformapi.security.AuthUtils;
import com.thiennth.blogplatformapi.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    public UserResponse get(Long id) {
        return userRepository.findById(id)
            .map(UserResponse::from)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public UserResponse updateProfile(Long id, UpdateUserProfileRequest request) {
        User currentUser = authUtils.currentUser();
        if (currentUser.getId() != id && currentUser.getRole().equals(User.Role.USER)) {
            throw new ForBiddenActionException();
        }
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.updateProfile(
            request.firstName(), 
            request.lastName(), 
            request.bio(), 
            request.avatarUrl());
        // userRepository.updateProfile(request.firstName(), request.lastName(), request.bio(), request.avatarUrl(), id);
        // userRepository.save(user);
        return UserResponse.from(userRepository.save(user));
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordRequest request) {
        User currentUser = authUtils.currentUser();
        if (currentUser.getId() != id) {
            throw new ForBiddenActionException();
        }
        log.info(currentUser.getPassword());
        if (!passwordEncoder.matches(request.oldPassword(), currentUser.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        if (request.newPassword().equals(request.oldPassword())) {
            throw new BadRequestException("New passowrd must be different from current password");
        }

        currentUser.changePassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(currentUser);
    }

    public PublishedPostsListByAuthorResponse getListPublishedPost(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Post> posts = postRepository.findByAuthorIdAndStatus(userId, Post.Status.PUBLISHED);
        return new PublishedPostsListByAuthorResponse(
            UserResponse.from(user),
            posts.stream().map(PostResponse::from).toList()
        );
    }
    
    @Transactional
    public void deactivate(Long userId) {
        Long currentId = authUtils.currentUserId();
        if (currentId == userId) {
            throw new ForBiddenActionException();
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.deactivate();
        userRepository.save(user);
    }
    
    public PaginatedResponse<UserResponse> getAll(UserFilterRequest filter) {
        Specification<User> spec = UserSpecification.from(filter);
        Page<User> page = userRepository.findAll(spec, filter.toPageable());
        log.debug("Query returned {} / {} total elements (page {}/{})",
            page.getNumberOfElements(), page.getTotalElements(),
            page.getNumber() + 1, page.getTotalPages());
        return PaginatedResponse.of("Success", page, UserResponse::from);
    }
}
