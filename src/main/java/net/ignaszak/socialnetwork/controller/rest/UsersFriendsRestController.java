package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Relation;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.dto.SuccessRestDTO;
import net.ignaszak.socialnetwork.exception.AppException;
import net.ignaszak.socialnetwork.exception.InvalidRelationException;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.service.relation.RelationService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest-api/users")
public class UsersFriendsRestController {

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
    public Page<User> getFriends(@PathVariable Integer id, Pageable page) throws NotFoundException {
        User user = userService.getUserById(id);
        return userService.getFriendsByUser(user, page);
    }

    @PutMapping(value = "/{id}/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public Relation addRelation(@PathVariable Integer id) throws AppException {
        User friend = userService.getUserById(id);
        Relation relation = new Relation(userService.getCurrentUser(), friend);
        relationService.add(relation);
        return relation;
    }

    @DeleteMapping(value = "/{id}/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessRestDTO deleteRelation(@PathVariable Integer id) throws NotFoundException {
        Relation relation = relationService.getRelationWithCurrentUserByUserId(id);
        relationService.delete(relation);
        return new SuccessRestDTO();
    }

    @GetMapping(value = "/{id}/friend", produces = MediaType.APPLICATION_JSON_VALUE)
    public Relation getRelation(@PathVariable Integer id) {
        return relationService.getRelationWithCurrentUserByUserIdOrGetEmptyRelation(id);
    }

    @PostMapping(value = "/{id}/friend", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessRestDTO acceptRelation(@PathVariable Integer id) throws AppException {
        Relation relation = relationService.getRelationWithCurrentUserByUserId(id);
        relationService.accept(relation);
        return new SuccessRestDTO();
    }
}
