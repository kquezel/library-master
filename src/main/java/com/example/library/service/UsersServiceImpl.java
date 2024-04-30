package com.example.library.service;

import com.example.library.dto.UserDto;
import com.example.library.exception.UsernameExistsException;
import com.example.library.model.User;
import com.example.library.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {


    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUser(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public void create(User user) {
        usersRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Optional<User> findById(long user_id) { return usersRepository.findById(user_id);}


    @Override
    public User getById(Long user_id) { return  usersRepository.getById(user_id); }

    @Override
    public User read(long user_id) {
        return usersRepository.getOne(user_id);
    }

    @Override
    public boolean update(User user, long user_id) {
        if (usersRepository.existsById(user_id)) {
            user.setUsersId(user_id);
            usersRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(long user_id) {
        if (usersRepository.existsById(user_id)) {
            usersRepository.deleteById(user_id);
            return true;
        }
        return false;
    }
    public User registerNewUserAccount(UserDto accountDto) throws UsernameExistsException {
        User userExist = usersRepository.findByUsername(accountDto.getUsername());
        if (userExist != null) {
            return null;
//            throw new UsernameExistsException(
//                    "There is an account with that username:" + accountDto.getUsername());
        }
        User user = new User();
        user.setFullName(accountDto.getFullName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEnabled(true);
        user.setUsername(accountDto.getUsername());
        user.setType(accountDto.getType());
        user.setBirth(accountDto.getBirth());
        return usersRepository.save(user);
    }


}