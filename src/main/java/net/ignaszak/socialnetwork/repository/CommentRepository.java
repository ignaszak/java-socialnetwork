package net.ignaszak.socialnetwork.repository;

import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findAllByPostOrderByCreatedDateDesc(Post post, Pageable page);
    Optional<Comment> findCommentById(Integer id);
}
