package net.ignaszak.socialnetwork.repository;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {

    Page<Media> findAllByAuthorOrderByCreatedDate(User author, Pageable page);
}
