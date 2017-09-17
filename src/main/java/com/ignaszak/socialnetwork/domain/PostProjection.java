package com.ignaszak.socialnetwork.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "postProjection", types = { Post.class })
public interface PostProjection {

    public Integer getId();
    public String getText();
    public Date getCreatedDate();
    public User getUser();
}
