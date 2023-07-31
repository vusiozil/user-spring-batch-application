package com.example.demo.config;

import com.example.demo.models.User;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class UserFieldSetMapper implements FieldSetMapper<User> {
  @Override
  public User mapFieldSet(FieldSet fieldSet) throws BindException{

    long id = fieldSet.readLong("id");
    String first_name = fieldSet.readString("first_name");
    String last_name = fieldSet.readString("last_name");
    String email = fieldSet.readString("email");
    String gender = fieldSet.readString("gender");

    return new User(id, first_name, last_name, email, gender);
  }
}
