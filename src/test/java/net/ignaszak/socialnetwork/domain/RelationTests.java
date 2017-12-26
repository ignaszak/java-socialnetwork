package net.ignaszak.socialnetwork.domain;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class RelationTests {

    @Test
    public void testGenerateSameKeyForUsersPair() {
        User sender = Mockito.mock(User.class);
        User receiver = Mockito.mock(User.class);
        Mockito.when(sender.getId()).thenReturn(1);
        Mockito.when(receiver.getId()).thenReturn(2);

        Relation relation = new Relation(sender, receiver);
        Assert.assertEquals("1-2", relation.getKey());

        relation = new Relation(receiver, sender);
        Assert.assertEquals("1-2", relation.getKey());
    }
}
