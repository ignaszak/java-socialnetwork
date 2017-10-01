package com.ignaszak.socialnetwork.repository;

import com.ignaszak.socialnetwork.domain.Comment;
import com.ignaszak.socialnetwork.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> queryAllByPostOrderByCreatedDateDesc(Post post, Pageable page);
    Comment findCommentById(Integer id);
}
