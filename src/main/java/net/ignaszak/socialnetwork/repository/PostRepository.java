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
    Page<Post> queryAllByUserOrderByCreatedDateDesc(User user, Pageable page);
    Post findById(Integer id);
    @Query(
        "SELECT DISTINCT p FROM Post p WHERE " +
            "p.user IN(SELECT r.sender FROM Relation r WHERE r.receiver = :user) " +
            "OR p.user IN(SELECT r.receiver FROM Relation r WHERE r.sender = :user) " +
            "OR p.user = :user " +
        "ORDER BY p.createdDate DESC"
    )
    Page<Post> queryFeedByUser(@Param("user") User user, Pageable page);
}
