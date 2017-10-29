package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Relation;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.InvalidRelationException;
import net.ignaszak.socialnetwork.exception.ResourceNotFoundException;
import net.ignaszak.socialnetwork.service.relation.RelationService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest-api/users")
public class UserFriendRestController {

    private UserService userService;
    private RelationService relationService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRelationService(RelationService relationService) {
        this.relationService = relationService;
    }

    @GetMapping(value = "/{id}/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> getFriends(@PathVariable Integer id, Pageable page) {
        User user = userService.getUserById(id);
        if (user == null) throw new ResourceNotFoundException();
        return userService.getFriendsByUser(user, page);
    }

    @PostMapping(value = "/{id}/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public Relation addRelation(@PathVariable Integer id) throws InvalidRelationException {
        User currentUser = userService.getCurrentUser();
        User friend = userService.getUserById(id);
        if (friend == null) throw new InvalidRelationException();
        Relation relation = new Relation(currentUser, friend);
        relationService.add(relation);
        return relation;
    }

    @DeleteMapping(value = "/{id}/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRelation(@PathVariable Integer id) {
        Relation relation = relationService.getRelationWithCurrentUserByUserId(id);
        relationService.delete(relation);
    }

    @GetMapping(value = "/{id}/friend", produces = MediaType.APPLICATION_JSON_VALUE)
    public Relation getRelation(@PathVariable Integer id) {
        return relationService.getRelationWithCurrentUserByUserId(id);
    }

    @PostMapping(value = "/{id}/friend", produces = MediaType.APPLICATION_JSON_VALUE)
    public void acceptRelation(@PathVariable Integer id) throws InvalidRelationException {
        Relation relation = relationService.getRelationWithCurrentUserByUserId(id);
        relationService.accept(relation);
    }
}
