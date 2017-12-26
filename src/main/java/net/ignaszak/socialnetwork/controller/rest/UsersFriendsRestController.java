package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Relation;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.dto.ErrorDTO;
import net.ignaszak.socialnetwork.exception.InvalidRelationException;
import net.ignaszak.socialnetwork.service.relation.RelationService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getFriends(@PathVariable Integer id, Pageable page) {
        User user = userService.getUserById(id);
        if (user == null) return new ResponseEntity<>(ErrorDTO.notFound(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userService.getFriendsByUser(user, page), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRelation(@PathVariable Integer id) throws InvalidRelationException {
        User friend = userService.getUserById(id);
        if (friend == null) return new ResponseEntity<>(ErrorDTO.notFound(), HttpStatus.NOT_FOUND);
        Relation relation = new Relation(userService.getCurrentUser(), friend);
        relationService.add(relation);
        return new ResponseEntity<>(relation, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRelation(@PathVariable Integer id) {
        Relation relation = relationService.getRelationWithCurrentUserByUserId(id);
        relationService.delete(relation);
    }

    @GetMapping(value = "/{id}/friend", produces = MediaType.APPLICATION_JSON_VALUE)
    public Relation getRelation(@PathVariable Integer id) {
        Relation relation = relationService.getRelationWithCurrentUserByUserId(id);
        if (relation == null) return new Relation();
        return relation;
    }

    @PostMapping(value = "/{id}/friend", produces = MediaType.APPLICATION_JSON_VALUE)
    public void acceptRelation(@PathVariable Integer id) throws InvalidRelationException {
        Relation relation = relationService.getRelationWithCurrentUserByUserId(id);
        relationService.accept(relation);
    }
}
