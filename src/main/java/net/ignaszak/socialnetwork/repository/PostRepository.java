package net.ignaszak.socialnetwork.repository;

import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findById(Integer id);

    @Query(
        "SELECT p FROM Post p LEFT JOIN p.medias m WHERE " +
            "p.author = :user " +
            "OR p.receiver = :user " +
            "OR p.author = :user " +
            "OR p IN(SELECT c.post FROM Comment c WHERE c.author = :user)" +
            "GROUP BY p.id " +
        "ORDER BY p.createdDate DESC"
    )
    Page<Post> findPostsByUser(@Param("user") User user, Pageable page);

    @Query(
        "SELECT p FROM Post p LEFT JOIN p.medias m WHERE " +
            "p.author IN(SELECT r.sender FROM Relation r WHERE r.receiver = :user) " +
            "OR p.author IN(SELECT r.receiver FROM Relation r WHERE r.sender = :user) " +
            "OR p.author = :user " +
            "OR p.receiver = :user " +
            "OR p IN(SELECT c.post FROM Comment c WHERE c.author = :user)" +
            "GROUP BY p.id " +
        "ORDER BY p.createdDate DESC"
    )
    Page<Post> findFeedByUser(@Param("user") User user, Pageable page);
}
