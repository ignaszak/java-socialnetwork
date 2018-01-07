package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Relation;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.service.relation.RelationService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UsersFriendsRestController.class, secure = false)
@EnableSpringDataWebSupport
public class UsersFriendsRestControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    RelationService relationService;
    @MockBean
    Pageable pageable;
    @MockBean
    Relation relation;
    @MockBean
    User user;

    @Before
    public void setUp() {
        User user = new User();
        user.setId(1);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
    }

    @Test
    public void shouldReturnNotFoundIfUserDoesNotExists() throws Exception {
        Mockito.when(userService.getUserById(1)).thenThrow(new NotFoundException());
        mockMvc
            .perform(get("/rest-api/users/{id}/friends", 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    public void shouldReturnUserFriends() throws Exception {
        Mockito.when(userService.getUserById(Mockito.anyInt())).thenReturn(user);
        mockMvc
            .perform(get("/rest-api/users/{id}/friends", 1))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhileAddingRelationToNoExistingUser() throws Exception {
        Mockito.when(userService.getUserById(1)).thenThrow(new NotFoundException());
        mockMvc
            .perform(put("/rest-api/users/{id}/friends", 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    public void shouldAddFriend() throws Exception {
        User user = new User();
        user.setId(2);
        Mockito.when(userService.getUserById(Mockito.anyInt())).thenReturn(user);
        mockMvc
            .perform(put("/rest-api/users/{id}/friends", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sender").isNotEmpty())
            .andExpect(jsonPath("$.receiver").isNotEmpty());
        Mockito.verify(relationService, Mockito.times(1)).add(Mockito.any());
    }

    @Test
    public void shouldDeleteFriend() throws Exception {
        Mockito.when(relationService.getRelationWithCurrentUserByUserIdOrGetEmptyRelation(Mockito.anyInt())).thenReturn(relation);
        mockMvc
            .perform(delete("/rest-api/users/{id}/friends", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        Mockito.verify(relationService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    public void shouldReturnRelation() throws Exception {
        User sender = new User();
        User receiver = new User();
        sender.setId(1);
        receiver.setId(2);
        Mockito.when(relationService.getRelationWithCurrentUserByUserIdOrGetEmptyRelation(Mockito.anyInt())).thenReturn(
            new Relation(sender, receiver)
        );
        mockMvc
            .perform(get("/rest-api/users/{id}/friend", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.key").value("1-2"));
    }

    @Test
    public void shouldReturnEmptyRelationWhenRelationDoesNotExists() throws Exception {
        Mockito.when(relationService.getRelationWithCurrentUserByUserIdOrGetEmptyRelation(1)).thenReturn(new Relation());
        mockMvc
            .perform(get("/rest-api/users/{id}/friend", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.key").isEmpty())
            .andExpect(jsonPath("$.sender").isEmpty())
            .andExpect(jsonPath("$.receiver").isEmpty())
            .andExpect(jsonPath("$.accepted").value(false));
    }

    @Test
    public void shouldAcceptRelation() throws Exception {
        Mockito.when(relationService.getRelationWithCurrentUserByUserIdOrGetEmptyRelation(Mockito.anyInt())).thenReturn(relation);
        mockMvc
            .perform(post("/rest-api/users/{id}/friend", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        Mockito.verify(relationService, Mockito.times(1)).accept(Mockito.any());
    }

    @Test
    public void shouldReturnErrorWhileAcceptingRelationIfRelationWithUserDoesNotExists() throws Exception {
        Mockito.when(relationService.getRelationWithCurrentUserByUserId(1)).thenThrow(new NotFoundException());
        mockMvc
            .perform(post("/rest-api/users/{id}/friend", 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Not found"));
        Mockito.verify(relationService, Mockito.never()).accept(Mockito.any());
    }
}
