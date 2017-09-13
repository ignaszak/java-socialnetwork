package com.ignaszak.socialnetwork.service.post;

import com.ignaszak.socialnetwork.domain.Post;
import com.ignaszak.socialnetwork.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post savePost(Post post) {
        postRepository.save(post);
        return post;
    }
}
