package net.ignaszak.socialnetwork.service.post;

import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Post save(Post post);
    Post getPostById(Integer id);
    Page<Post> getAll(Pageable page);
    Page<Post> getByUser(User user, Pageable page);
    Page<Post> getFeedByUser(User user, Pageable page);
    void delete(Post post);
}
