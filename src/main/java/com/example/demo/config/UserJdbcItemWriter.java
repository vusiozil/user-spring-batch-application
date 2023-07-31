package com.example.demo.config;

import com.example.demo.models.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserJdbcItemWriter implements ItemWriter<User> {

  private static final String INSERT_USER = "INSERT INTO users(id, first_name,last_name, email, " +
          "gender) VALUES(?,?,?,?,?)";
  private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, " +
          "email=?,gender=? WHERE id=?";

  @Autowired
  private JdbcTemplate jdbcTemplate;


  @Override
  public void write(List<? extends User> list) throws Exception{

    for(User user : list){
      int updated = jdbcTemplate.update(UPDATE_USER, user.getFirst_name(), user.getLast_name(),
              user.getEmail(), user.getGender(),user.getId());
      if(updated==0){
         jdbcTemplate.update(INSERT_USER, user.getId(), user.getFirst_name(),
                user.getLast_name(), user.getEmail(), user.getGender());
      }
    }

  }
}
