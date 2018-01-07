package net.ignaszak.socialnetwork.service.comment;

import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Page<Comment> getCommentsByPost(Post post, Pageable page) {
        return commentRepository.findAllByPostOrderByCreatedDateDesc(post, page);
    }

    @Override
    public Comment getCommentById(Integer id) throws NotFoundException {
        return commentRepository.findCommentById(id).orElseThrow(NotFoundException::new);
    }
}
