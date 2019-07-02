package com.github.pedrobacchini.introspringdynamodb.service;

import com.github.pedrobacchini.introspringdynamodb.domain.User;
import com.github.pedrobacchini.introspringdynamodb.exception.InvalidLoadingDataFile;
import com.github.pedrobacchini.introspringdynamodb.exception.UserNotFoundException;
import com.github.pedrobacchini.introspringdynamodb.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public List<User> findAll() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public User save(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    public void delete(String id) {
        try {
            userRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new UserNotFoundException(e);
        }
    }

    public void createTable() { userRepository.createTable(); }

    public void loadData() {
        try {
            userRepository.loadData();
        } catch(IOException e) {
            throw new InvalidLoadingDataFile(e);
        }
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User update(String id, User user) {
        User savedUser = findById(id);
        BeanUtils.copyProperties(user, savedUser, "id");
        return userRepository.save(savedUser);
    }

    public void deleteAll() { userRepository.deleteAll(); }
}
