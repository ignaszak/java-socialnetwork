package com.ignaszak.socialnetwork.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "postProjection", types = { Post.class })
public interface PostProjection {

    Integer getId();
    String getText();
    Date getCreatedDate();
    User getUser();
}
