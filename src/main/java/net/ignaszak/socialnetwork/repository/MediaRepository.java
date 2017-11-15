package net.ignaszak.socialnetwork.repository;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {

    Page<Media> findAllByAuthorOrderByCreatedDate(User author, Pageable page);
    Set<Media> findAllByKey(Integer key);

    @Modifying
    @Query("UPDATE Media m SET m.key = NULL, m.post = :post WHERE m.key = :key")
    void attachMediasToPostByKey(@Param("post") Post post, @Param("key") Integer key);
}
