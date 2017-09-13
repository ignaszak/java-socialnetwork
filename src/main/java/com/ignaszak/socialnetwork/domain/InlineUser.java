package com.ignaszak.socialnetwork.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "inlineUser", types = { Post.class })
public interface InlineUser {

    public Integer getId();
    public String getText();
    public Date getCreatedDate();
    public User getUser();
}
