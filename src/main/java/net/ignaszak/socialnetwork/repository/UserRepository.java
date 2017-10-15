package net.ignaszak.socialnetwork.repository;

import net.ignaszak.socialnetwork.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.List;


/**
 * Created by tomek on 05.04.17.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmailOrNewEmail(String email, String newEmail);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User findUserByActivationCode(String code);

    @Query("SELECT u FROM User u WHERE u.username = ?#{principal.username}")
    List<User> findCurrentUser();

    @Query(
        "SELECT DISTINCT u FROM User u WHERE u != :user AND " +
        "(u IN(SELECT r.sender FROM Relation r WHERE r.isAccepted = :accept AND r.receiver = :user) " +
        "OR u IN(SELECT r.receiver FROM Relation r WHERE r.isAccepted = :accept AND r.sender = :user))"
    )
    Page<User> findFriendsByUserAndRelationStatus(@Param("user") User user, @Param("accept") boolean accept, Pageable page);
}
