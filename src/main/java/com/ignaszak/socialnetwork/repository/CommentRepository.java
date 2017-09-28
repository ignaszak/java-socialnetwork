package com.ignaszak.socialnetwork.repository;

import com.ignaszak.socialnetwork.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {

    Page<Comment> queryAllByPost_IdOrderByCreatedDateDesc(@Param("postId") Integer postId, Pageable page);
}
