package com.sde.bookstore.repository;

import com.sde.bookstore.domain.OrderMain;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderMainRepository extends CrudRepository<OrderMain,Long> {

    Iterable<OrderMain> findByUserUserId(Long userId);
}
