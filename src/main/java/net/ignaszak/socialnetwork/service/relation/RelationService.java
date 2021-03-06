package net.ignaszak.socialnetwork.service.relation;

import net.ignaszak.socialnetwork.domain.Relation;
import net.ignaszak.socialnetwork.exception.InvalidRelationException;
import net.ignaszak.socialnetwork.exception.NotFoundException;

public interface RelationService {

    void add(Relation relation) throws InvalidRelationException;
    void delete(Relation relation);
    void accept(Relation relation) throws InvalidRelationException;
    Relation getRelationWithCurrentUserByUserId(Integer id) throws NotFoundException;
    Relation getRelationWithCurrentUserByUserIdOrGetEmptyRelation(Integer id);
    Integer countInvitationsByCurrentUser();
}
