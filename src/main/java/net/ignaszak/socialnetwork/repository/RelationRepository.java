package net.ignaszak.socialnetwork.repository;

import net.ignaszak.socialnetwork.domain.Relation;
import net.ignaszak.socialnetwork.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Integer> {

    Optional<Relation> findRelationBySender_IdAndReceiver_Id(Integer senderId, Integer receiverId);
    Integer countByReceiverAndAcceptedIsFalseOrAcceptedIsNull(User receiver);
}
