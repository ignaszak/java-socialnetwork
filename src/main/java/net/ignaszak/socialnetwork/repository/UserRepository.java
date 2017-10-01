package net.ignaszak.socialnetwork.repository;

import net.ignaszak.socialnetwork.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
