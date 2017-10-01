package net.ignaszak.socialnetwork.domain;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userProjection", types = { User.class })
public interface UserProjection {

    Integer getId();
    String getEmail();
    String getUsername();
    String getRole();
    String getCaption();
    String getStatus();
    boolean isEnabled();
}
