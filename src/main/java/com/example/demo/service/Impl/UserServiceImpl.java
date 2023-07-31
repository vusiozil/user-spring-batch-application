package com.example.demo.service.Impl;

import com.example.demo.models.User;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UsersRepository usersRepository;

  public UserServiceImpl(UsersRepository usersRepository){
    this.usersRepository = usersRepository;
  }

  @Override
  public List<User> getUsers(){
    return usersRepository.findAll();
  }

  @Override
  public Optional<User> getUserById(Long userId){
    return usersRepository.findById(userId);
  }

  @Override
  public User saveUser(User user){
    return usersRepository.save(user);
  }

  @Override
  public void updateUser(User user){
    usersRepository.save(user);
  }

  @Override
  public void deleteUserById(Long Id){
    usersRepository.deleteById(Id);
  }
}
