package com.sde.bookstore.repository;

import com.sde.bookstore.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByUserName(String name);

    Optional<User> findByUserId(Long id);
}
