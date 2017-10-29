package net.ignaszak.socialnetwork.service.post;

import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post save(Post post) {
        postRepository.save(post);
        return post;
    }

    @Override
    public Post getPostById(Integer id) {
        return postRepository.findById(id);
    }

    @Override
    public Page<Post> getAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> getByUser(User user, Pageable page) {
        return postRepository.findPostsByUser(user, page);
    }

    @Override
    public Page<Post> getFeedByUser(User user, Pageable page) {
        return postRepository.findFeedByUser(user, page);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
