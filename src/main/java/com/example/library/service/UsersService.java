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
    Optional<User> findById(long id);
    boolean update(User user, long id);
    boolean delete(long id);
    User registerNewUserAccount(UserDto accountDto) throws UsernameExistsException;

}
