package com.github.pedrobacchini.introspringdynamodb.repository;

import com.github.pedrobacchini.introspringdynamodb.domain.User;
import com.github.pedrobacchini.introspringdynamodb.repository.user.CustomUserRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface UserRepository extends CrudRepository<User, String>, CustomUserRepository {

    List<User> findByLastName(String lastName);

    List<User> findByFirstName(String firstName);
}
