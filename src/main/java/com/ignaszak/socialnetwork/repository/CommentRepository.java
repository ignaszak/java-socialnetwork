package com.ignaszak.socialnetwork.repository;

import com.ignaszak.socialnetwork.domain.Comment;
import com.ignaszak.socialnetwork.domain.CommentProjection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = CommentProjection.class)
public interface CommentRepository  extends PagingAndSortingRepository<Comment, Integer> {
}
