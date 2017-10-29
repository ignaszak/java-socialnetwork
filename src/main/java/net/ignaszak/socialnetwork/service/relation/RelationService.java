package net.ignaszak.socialnetwork.service.relation;

import net.ignaszak.socialnetwork.domain.Relation;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.InvalidRelationException;

public interface RelationService {

    void add(Relation relation) throws InvalidRelationException;
    void delete(Relation relation);
    void accept(Relation relation) throws InvalidRelationException;
    Relation getRelationWithCurrentUserByUserId(Integer id);
    Integer countInvitationsByCurrentUser();
}
