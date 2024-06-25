package com.example.library.service;

import com.example.library.dto.UserDto;
import com.example.library.exception.UsernameExistsException;
import com.example.library.model.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    User findUser(String username);
    void create(User user);
    List<User> findAll();
    Optional<User> findById(long user_id);
    boolean update(User user, long user_id);
    boolean delete(long user_id);
    User registerNewUserAccount(UserDto accountDto) throws UsernameExistsException;

}
