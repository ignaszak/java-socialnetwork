package net.ignaszak.socialnetwork.service;

import net.ignaszak.socialnetwork.domain.Relation;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.InvalidRelationException;
import net.ignaszak.socialnetwork.repository.RelationRepository;
import net.ignaszak.socialnetwork.service.relation.RelationService;
import net.ignaszak.socialnetwork.service.relation.RelationServiceImpl;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RelationServiceTests {

    @TestConfiguration
    static class RelationServiceImplTestContextConfiguration {
        @Bean
        public RelationService relationService() {
            return new RelationServiceImpl();
        }
    }

    @Autowired
    private RelationService relationService;

    @MockBean
    private RelationRepository relationRepository;

    @MockBean
    private UserService userService;

    @Test(expected = InvalidRelationException.class)
    public void testAddInvalidRelation() throws InvalidRelationException {
        User sender = Mockito.mock(User.class);
        User receiver = Mockito.mock(User.class);
        Relation relation = Mockito.mock(Relation.class);
        Mockito.when(relation.getSender()).thenReturn(sender);
        Mockito.when(relation.getReceiver()).thenReturn(receiver);
        Mockito.when(receiver.isEqualsTo(sender)).thenReturn(true);
        relationService.add(relation);
    }

    @Test
    public void testAddRelation() throws InvalidRelationException {
        User sender = Mockito.mock(User.class);
        User receiver = Mockito.mock(User.class);
        Relation relation = Mockito.mock(Relation.class);
        Mockito.when(relation.getSender()).thenReturn(sender);
        Mockito.when(relation.getReceiver()).thenReturn(receiver);
        Mockito.when(receiver.isEqualsTo(sender)).thenReturn(false);
        relationService.add(relation);
        Mockito.verify(relationRepository, Mockito.times(1)).save(relation);
    }
}
