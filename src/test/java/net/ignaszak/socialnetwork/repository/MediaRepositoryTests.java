package net.ignaszak.socialnetwork.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

@DatabaseSetup("classpath:data/mediaRepositoryDataset.xml")
public class MediaRepositoryTests extends RepositoryTestsInitializer {

    @Autowired
    private MediaRepository mediaRepository;
    @MockBean
    private Pageable pageable;

    @Test
    public void findAllByAuthorAndKeyIsNullOrderByCreatedDate() {
        User user = new User();
        user.setId(1);
        Page<Media> medias = mediaRepository.findAllByAuthorAndKeyIsNullOrderByCreatedDate(user, pageable);
        Assert.assertEquals(2, medias.getTotalElements());
    }

    @Test
    public void findAllByKey() {
        Set<Media> medias = mediaRepository.findAllByKey("uniqueKey");
        Assert.assertEquals(4, medias.size());
    }

    @Test
    public void findAllByPost_IdOrderById() {
        Set<Media> medias = mediaRepository.findAllByPost_IdOrderById(1);
        Assert.assertEquals(2, medias.size());
    }
}
