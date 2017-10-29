package net.ignaszak.socialnetwork.service.relation;

import net.ignaszak.socialnetwork.domain.Relation;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.InvalidRelationException;
import net.ignaszak.socialnetwork.repository.RelationRepository;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationServiceImpl implements RelationService {

    private RelationRepository relationRepository;
    private UserService userService;

    @Autowired
    public void setRelationRepository(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void add(Relation relation) throws InvalidRelationException {
        if (relation.getReceiver().isEqualsTo(relation.getSender()))
            throw new InvalidRelationException();
        relationRepository.save(relation);
    }

    @Override
    public void delete(Relation relation) {
        relationRepository.delete(relation);
    }

    @Override
    public void accept(Relation relation) throws InvalidRelationException {
        relation.accept();
        add(relation);
    }

    @Override
    public Relation getRelationWithCurrentUserByUserId(Integer id) {
        User currentUser = userService.getCurrentUser();
        Relation relation = relationRepository.findRelationBySender_IdAndReceiver_Id(currentUser.getId(), id);
        if (relation == null) {
            return relationRepository.findRelationBySender_IdAndReceiver_Id(id, currentUser.getId());
        }
        return relation;
    }

    @Override
    public Integer countInvitationsByCurrentUser() {
        return relationRepository.countByReceiverAndAcceptedIsFalse(userService.getCurrentUser());
    }
}
