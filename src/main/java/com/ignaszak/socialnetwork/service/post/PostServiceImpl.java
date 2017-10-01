package com.ignaszak.socialnetwork.service.post;

import com.ignaszak.socialnetwork.domain.Post;
import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.repository.PostRepository;
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
        return postRepository.queryAllByUserOrderByCreatedDateDesc(user, page);
    }

    @Override
    public Page<Post> getFeedByUser(User user, Pageable page) {
        return postRepository.queryAllByUserOrderByCreatedDateDesc(user, page);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
