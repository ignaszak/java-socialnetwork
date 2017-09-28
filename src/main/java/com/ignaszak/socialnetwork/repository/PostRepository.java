package com.ignaszak.socialnetwork.repository;

import com.ignaszak.socialnetwork.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query("SELECT p FROM Post p LEFT JOIN p.user u WHERE u.username = ?#{principal.username} ORDER BY p.createdDate DESC")
    Page<Post> queryAllByCurrentUser(Pageable page);
    Page<Post> queryAllByUser_UsernameOrderByCreatedDateDesc(@Param("username") String username, Pageable page);
    Post findById(Integer id);
}
