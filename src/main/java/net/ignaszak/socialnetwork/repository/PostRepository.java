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
        "SELECT new map(p.id AS id, p.author AS author, p.text AS text, p.createdDate AS createdDate) FROM Post p WHERE " +
            "p.author = :user " +
            "OR p.receiver = :user " +
            "OR p.author = :user " +
            "OR p IN(SELECT c.post FROM Comment c WHERE c.author = :user) " +
        "ORDER BY p.createdDate DESC"
    )
    Page<Post> findPostsByUser(@Param("user") User user, Pageable page);

    @Query(
        "SELECT new map(p.id AS id, p.author AS author, p.receiver AS receiver, p.text AS text, p.createdDate AS createdDate) FROM Post p WHERE " +
            "p.author IN(SELECT r.sender FROM Relation r WHERE r.receiver = :user) " +
            "OR p.author IN(SELECT r.receiver FROM Relation r WHERE r.sender = :user) " +
            "OR p.author = :user " +
            "OR p.receiver = :user " +
            "OR p IN(SELECT c.post FROM Comment c WHERE c.author = :user) " +
        "ORDER BY p.createdDate DESC"
    )
    Page<Post> findFeedByUser(@Param("user") User user, Pageable page);
}
