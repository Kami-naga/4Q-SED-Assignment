package com.sde.bookstore.service;

import com.sde.bookstore.domain.Book;
import com.sde.bookstore.domain.OrderMain;
import com.sde.bookstore.forms.BookForm;
import com.sde.bookstore.forms.OrderForm;

import java.util.Optional;

public interface OrderService {

    Iterable<OrderMain> findAllOrders();

    Iterable<OrderMain> findOrderByUserId(Long id);

    Optional<OrderMain> addOrder(OrderForm orderForm);

    Boolean deleteOrder(Long orderId);
}
