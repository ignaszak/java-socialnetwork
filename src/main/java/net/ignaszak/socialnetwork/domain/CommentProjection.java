package net.ignaszak.socialnetwork.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "commentProjection", types = { Comment.class })
public interface CommentProjection {

    Integer getId();
    User getUser();
    String getText();
    Date getCreatedDate();
}
