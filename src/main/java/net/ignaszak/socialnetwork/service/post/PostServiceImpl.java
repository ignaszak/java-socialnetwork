package net.ignaszak.socialnetwork.service.post;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.EmptyPostException;
import net.ignaszak.socialnetwork.repository.MediaRepository;
import net.ignaszak.socialnetwork.repository.PostRepository;
import net.ignaszak.socialnetwork.service.media.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private MediaService mediaService;
    private MediaRepository mediaRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setMediaService(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Autowired
    public void setMediaRepository(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Override
    public Post save(Post post) {
        Set<Media> medias = mediaService.movePostMediasFromTemp(post);
        if (medias.isEmpty() && post.getText().isEmpty()) throw new EmptyPostException();
        post.setMedias(medias);
        postRepository.save(post);
        mediaRepository.removeKeysFromAttachedMediasToPost(post);

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
