package com.sde.bookstore.controller;

import com.sde.bookstore.VO.OrderItemVO;
import com.sde.bookstore.VO.OrderVO;
import com.sde.bookstore.domain.User;
import com.sde.bookstore.enums.UserRoleType;
import com.sde.bookstore.forms.OrderForm;
import com.sde.bookstore.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value="get all orders")
    @GetMapping("/")
    public ResponseEntity<List<OrderVO>> getAllOrders(HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("User");
        if(userInfo == null || !UserRoleType.ADMINISTRATOR.getRoleName().equals(userInfo.getRole().getRoleName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<OrderVO> orderVOList = new ArrayList<>();
        orderService.findAllOrders().forEach(order -> {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            orderVO.setUserId(order.getUser().getUserId());
            ArrayList<OrderItemVO> orderItemVOs = new ArrayList<>();
            order.getOrderItems().forEach(orderItem -> {
                OrderItemVO orderItemVO = new OrderItemVO();
                orderItemVO.setAmount(orderItem.getAmount());
                orderItemVO.setBookId(orderItem.getBook().getBookId());
                orderItemVO.setBookName(orderItem.getBook().getBookName());
                orderItemVOs.add(orderItemVO);
            });
            orderVO.setOrderItems(orderItemVOs);
            orderVOList.add(orderVO);
        });
        return new ResponseEntity<>(orderVOList, HttpStatus.OK);
    }

    @ApiOperation(value="get orders by id")
    @GetMapping("/byUser")
    public ResponseEntity<List<OrderVO>> getOrdersByUser(HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("User");
        if(userInfo == null || !UserRoleType.ADMINISTRATOR.getRoleName().equals(userInfo.getRole().getRoleName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userInfo.getUserId();
        List<OrderVO> orderVOList = new ArrayList<>();
        orderService.findOrderByUserId(userId).forEach(order -> {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            orderVO.setUserId(order.getUser().getUserId());
            ArrayList<OrderItemVO> orderItemVOs = new ArrayList<>();
            order.getOrderItems().forEach(orderItem -> {
                OrderItemVO orderItemVO = new OrderItemVO();
                orderItemVO.setAmount(orderItem.getAmount());
                orderItemVO.setBookId(orderItem.getBook().getBookId());
                orderItemVO.setBookName(orderItem.getBook().getBookName());
                orderItemVOs.add(orderItemVO);
            });
            orderVO.setOrderItems(orderItemVOs);
            orderVOList.add(orderVO);
        });
        return new ResponseEntity<>(orderVOList, HttpStatus.OK);
    }

    @ApiOperation(value="add new orders")
    @PostMapping("/new")
    public ResponseEntity<OrderVO> addOrder(@RequestBody OrderForm orderForm, HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("User");
        if(userInfo == null || !UserRoleType.CUSTOM.getRoleName().equals(userInfo.getRole().getRoleName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return orderService.addOrder(orderForm).map(order -> {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order,orderVO);
            orderVO.setUserId(order.getUser().getUserId());
            ArrayList<OrderItemVO> orderItemVOs = new ArrayList<>();
            order.getOrderItems().forEach(orderItem -> {
                OrderItemVO orderItemVO = new OrderItemVO();
                orderItemVO.setAmount(orderItem.getAmount());
                orderItemVO.setBookId(orderItem.getBook().getBookId());
                orderItemVO.setBookName(orderItem.getBook().getBookName());
                orderItemVOs.add(orderItemVO);
            });
            orderVO.setOrderItems(orderItemVOs);
            return new ResponseEntity<>(orderVO,HttpStatus.CREATED);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.CONFLICT));
    }


    @ApiOperation(value="delete orders")
    @DeleteMapping("/")
    public ResponseEntity<Boolean> deleteOrder(@RequestParam("orderId") Long orderId,HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("User");
        if(userInfo == null || !UserRoleType.ADMINISTRATOR.getRoleName().equals(userInfo.getRole().getRoleName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(orderService.deleteOrder(orderId)) {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
