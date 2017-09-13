package com.ignaszak.socialnetwork.repository;

import com.ignaszak.socialnetwork.domain.InlineUser;
import com.ignaszak.socialnetwork.domain.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = InlineUser.class)
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query("SELECT p FROM Post p LEFT JOIN p.user u WHERE u.username = ?#{principal.username} ORDER BY p.createdDate DESC")
    List<Post> findAllByCurrentUser();
    List<Post> findAllByUser_Username(@Param("username") String username);
}
