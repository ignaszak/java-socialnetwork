package com.ignaszak.socialnetwork.repository;

import com.ignaszak.socialnetwork.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * Created by tomek on 05.04.17.
 */
@RepositoryRestResource()
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findUserByEmailOrUsername(String email, String username);
    User findUserByUsername(@Param("username") String username);
    User findUserByEmail(@Param("email") String email);
    User findUserByEmailOrNewEmail(@Param("email") String email, @Param("email") String newEmail);
    User findUserByActivationCode(String code);

    @Query("SELECT u FROM User u WHERE u.username = ?#{principal.username}")
    List<User> findCurrentUser();

    /*@PreAuthorize("principal.username == #user.username || hasRole('ROLE_ADMIN')")
    @Override
    default  <S extends User> S save(S user) {
        return user;
    }

    @PreAuthorize("principal.username == #user.username || hasRole('ROLE_ADMIN')")
    @Override
    void delete(@P("user") User user);*/


}
