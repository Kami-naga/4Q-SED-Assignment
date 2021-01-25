package com.sde.bookstore.service.impl;

import com.sde.bookstore.domain.OrderItem;
import com.sde.bookstore.domain.OrderMain;
import com.sde.bookstore.domain.User;
import com.sde.bookstore.forms.OrderForm;
import com.sde.bookstore.repository.BookRepository;
import com.sde.bookstore.repository.OrderItemRepository;
import com.sde.bookstore.repository.OrderMainRepository;
import com.sde.bookstore.repository.UserRepository;
import com.sde.bookstore.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMainRepository orderMainRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public OrderServiceImpl(OrderMainRepository orderMainRepository, OrderItemRepository orderItemRepository, UserRepository userRepository, BookRepository bookRepository){
        this.orderMainRepository = orderMainRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }
    @Override
    public Iterable<OrderMain> findAllOrders() {
        return orderMainRepository.findAll();
    }

    @Override
    public Iterable<OrderMain> findOrderByUserId(Long id) {
        return orderMainRepository.findByUserUserId(id);
    }

    @Override
    public Optional<OrderMain> addOrder(OrderForm orderForm) {
        OrderMain orderMain = new OrderMain();
        BeanUtils.copyProperties(orderForm, orderMain);
        User user = userRepository.findByUserId(orderForm.getUserId()).get();
        orderMain.setUser(user);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        orderForm.getOrderItems().forEach(orderItemForm -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setAmount(orderItemForm.getAmount());
            orderItem.setBook(bookRepository.findByBookId(orderItemForm.getBookId()).get());
            orderItem.setOrderMain(orderMain);
            orderItems.add(orderItem);
        });
        orderMain.setOrderItems(orderItems);
        return Optional.of(orderMainRepository.save(orderMain));
    }

    @Override
    public Boolean deleteOrder(Long orderId) {
        orderMainRepository.deleteById(orderId);
        return Boolean.TRUE;
    }
}
