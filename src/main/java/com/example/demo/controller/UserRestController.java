package com.example.demo.controller;

import com.example.demo.models.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

  @Autowired
  UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> users() {

    return ResponseEntity.ok(userService.getUsers());
  }

  @GetMapping("/{Id}")
  public  ResponseEntity<User> getUser(@PathVariable("Id") Long userId) {

    Optional<User> user = userService.getUserById(userId);
    return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<User> saveUser(@RequestBody User user) throws URISyntaxException{

    User savedUser = userService.saveUser(user);
    URI uri = new URI("/users/{user}");
    return ResponseEntity
            .created(uri)
            .build();
  }

}
