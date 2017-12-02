package net.ignaszak.socialnetwork.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.ignaszak.socialnetwork.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseSetup("classpath:data/relationRepositoryDataset.xml")
public class RelationRepositoryTests extends RepositoryTestsInitializer {

    @Autowired
    private RelationRepository relationRepository;

    @Test
    public void findRelationBySender_IdAndReceiver_Id() {
        Assert.assertNull(
            relationRepository.findRelationBySender_IdAndReceiver_Id(1, 2)
        );
        Assert.assertEquals(
            "2-3",
            relationRepository.findRelationBySender_IdAndReceiver_Id(2, 3).getKey()
        );
    }

    @Test
    public void countByReceiverAndAcceptedIsFalse() {
        User user = new User();
        user.setId(1);
        long count = (long) relationRepository.countByReceiverAndAcceptedIsFalseOrAcceptedIsNull(user);
        Assert.assertEquals(1, count);
    }
}
