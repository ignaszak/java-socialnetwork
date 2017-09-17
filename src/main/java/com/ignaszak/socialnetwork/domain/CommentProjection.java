package com.ignaszak.socialnetwork.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "commentProjection", types = { Comment.class })
public interface CommentProjection {

    public Integer getId();
    public User getUser();
    public String getText();
    public Date getCreatedDate();
}
