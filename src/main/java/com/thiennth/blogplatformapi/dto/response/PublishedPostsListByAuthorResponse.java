package com.thiennth.blogplatformapi.dto.response;

import java.util.List;

public record PublishedPostsListByAuthorResponse(
    UserResponse author,
    List<PostResponse> posts
) {
    
}
