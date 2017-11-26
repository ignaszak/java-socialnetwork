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
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {

    Page<Media> findAllByAuthorOrderByCreatedDate(User author, Pageable page);
    Set<Media> findAllByKey(String key);
    Set<Media> findAllByPost_IdOrderById(Integer postId);

    @Modifying
    @Transactional
    @Query("UPDATE Media m SET m.key = NULL WHERE m.post = :post")
    void removeKeysFromAttachedMediasToPost(@Param("post") Post post);
}
