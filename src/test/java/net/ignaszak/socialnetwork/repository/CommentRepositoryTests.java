package net.ignaszak.socialnetwork.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.Post;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@DatabaseSetup("classpath:data/commentRepositoryDataset.xml")
public class CommentRepositoryTests extends RepositoryTestsInitializer {

    @Autowired
    private CommentRepository commentRepository;
    @MockBean
    private Pageable pageable;
    @MockBean
    private Comment emptyComment;

    @Test
    public void findAllbyPost() {
        Post post = new Post();
        post.setId(1);
        Page<Comment> comments = commentRepository.findAllByPostOrderByCreatedDateDesc(post, pageable);
        Assert.assertEquals(7, comments.getTotalElements());
    }

    @Test
    public void findById() {
        Assert.assertEquals("Test comment 1", commentRepository.findCommentById(1).orElse(emptyComment).getText());
    }
}
