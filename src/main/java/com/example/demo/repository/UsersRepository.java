package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface UsersRepository extends CrudRepository<User,Long> {

  List<User> findAll();
}
