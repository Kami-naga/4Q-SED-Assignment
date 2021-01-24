package com.sde.bookstore.repository;

import com.sde.bookstore.domain.OrderItem;
import com.sde.bookstore.domain.OrderMain;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderItemRepository extends CrudRepository<OrderItem,Long> {

}
