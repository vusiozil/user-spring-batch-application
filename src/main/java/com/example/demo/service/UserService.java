package com.example.demo.service;

import com.example.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

  List<User> getUsers();

  Optional<User> getUserById(Long userId);

  User saveUser(User user);

  void updateUser(User user);

  void deleteUserById(Long userId);
}
