package net.ignaszak.socialnetwork.service.comment;

import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<Comment> getCommentsByPost(Post post, Pageable page);
    Comment getCommentById(Integer id);
    Comment save(Comment comment);
    void delete(Comment comment);
}
