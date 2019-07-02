package com.github.pedrobacchini.introspringdynamodb.resource;

import com.github.pedrobacchini.introspringdynamodb.domain.User;
import com.github.pedrobacchini.introspringdynamodb.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) { this.userService = userService; }

    @GetMapping("/table")
    public ResponseEntity createTable() {
        userService.createTable();
        return ResponseEntity.ok("Table Created!");
    }

    @GetMapping("/data")
    public List<User> loadData() {
        userService.loadData();
        return userService.findAll();
    }

    @GetMapping
    public List<User> getAllUsers() { return userService.findAll(); }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") String id) { return userService.findById(id); }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        User savedUser = userService.save(user);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = uriComponentsBuilder
                .path("/users/")
                .path(String.valueOf(savedUser.getId()))
                .build()
                .toUri();
        headers.setLocation(locationUri);

        return new ResponseEntity<>(savedUser, headers, HttpStatus.CREATED);
    }

    @PutMapping("{userId}")
    public User updateUser(@PathVariable("userId") String id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String id) { userService.delete(id); }

    @DeleteMapping
    public void deleteAllUser() { userService.deleteAll(); }
}
