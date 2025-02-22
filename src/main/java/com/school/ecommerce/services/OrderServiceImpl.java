package com.school.ecommerce.services;

import com.school.ecommerce.controllers.dtos.requests.UpdateOrderRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.controllers.exceptions.AccessDeniedException;
import com.school.ecommerce.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerce.entities.Order;
import com.school.ecommerce.entities.OrderStatus;
import com.school.ecommerce.entities.User;
import com.school.ecommerce.repositories.IOrderRepository;
import com.school.ecommerce.security.UserDetailsImpl;
import com.school.ecommerce.services.interfaces.IOrderService;
import com.school.ecommerce.services.interfaces.IOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private IOrderStatusService orderStatusService;

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public BaseResponse list() {
        List<Order> orders = repository.findAll();

        return BaseResponse.builder()
                .data(orders)
                .message("Orders were found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse create() {

        User userAuthenticated = getUserAuthenticated().getUser();

        Order order;

        Optional<Order> orderOptional = repository.getOneByOrderStatus_NameAndUser_Id("PENDING", userAuthenticated.getId());

        order = orderOptional.orElseGet(() -> create(userAuthenticated));

        return BaseResponse.builder()
                .data(order)
                .message("Order created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private Order create(User userAuthenticated) {
        Order order = new Order();
        OrderStatus defaultOrderStatus = orderStatusService.findOneAndEnsureExistByName("PENDING");

        order.setOrderStatus(defaultOrderStatus);
        order.setUser(userAuthenticated);
        order.setOrderItems(Collections.EMPTY_LIST);

        order = repository.save(order);
        return order;
    }

    @Override
    public BaseResponse get(Long id) {

        UserDetailsImpl userDetails = getUserAuthenticated();

        Order order = findOneAndEnsureExistById(id);

        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                && !userDetails.getUser().getId().equals(order.getUser().getId()))
            throw new AccessDeniedException();

        return BaseResponse.builder()
                .data(order)
                .message("Order already exists")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateOrderRequest request) {

        Order order = findOneAndEnsureExistById(id);

        OrderStatus orderStatus = orderStatusService.findOneAndEnsureExistById(request.getOrderStatusId());

        order = update(order, orderStatus);

        return BaseResponse.builder()
                .data(order)
                .message("Order updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {

        User userAuthenticated = getUserAuthenticated().getUser();

        Order order = findOneAndEnsureExistById(id);

        if (!order.getUser().getId().equals(userAuthenticated.getId()))
            throw new AccessDeniedException();

        if (order.getOrderStatus().getName().equals("DELIVERED")
                || order.getOrderStatus().getName().equals("IN_PROGRESS")) {

            return BaseResponse.builder()
                    .data(Collections.EMPTY_LIST)
                    .message("Order deleted unsuccessful")
                    .success(Boolean.FALSE)
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .build();
        }

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("Order deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public BaseResponse getOrderByUserId(Long id) {
        UserDetailsImpl userDetails = getUserAuthenticated();
        User userAuthenticated = userDetails.getUser();

        Long userId = id != null ? id : userAuthenticated.getId();

        Order order = repository.getOneByOrderStatus_NameAndUser_Id("PENDING",userId)
                .orElseThrow(() -> new ObjectNotFoundException("Order Not Found"));

        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                && !userDetails.getUser().getId().equals(order.getUser().getId())) {
            throw new AccessDeniedException();
        }

        return BaseResponse.builder()
                .data(order)
                .message("Order already exists")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public BaseResponse getDeliveredAndInProgressOrderByUserId(Long id) {
        UserDetailsImpl userDetails = getUserAuthenticated();
        User userAuthenticated = userDetails.getUser();
        List<Order> orderList = new ArrayList<>();

        Long userId = id!=null ? id : userAuthenticated.getId();

        List<Order> inProgressOrders = repository.getAllByOrderStatus_NameAndUser_Id("IN_PROGRESS", userId);
        List<Order> deliveredOrders = repository.getAllByOrderStatus_NameAndUser_Id("DELIVERED", userId);

        orderList.addAll(inProgressOrders);
        orderList.addAll(deliveredOrders);

        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                && !userDetails.getUser().getId().equals(orderList.get(0).getUser().getId())) {
            throw new AccessDeniedException();
        }

        return BaseResponse.builder()
                .data(orderList)
                .message("Order already exists")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }


    @Override
    public Order findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Order not found"));
    }

    @Override
    public Order findOneAndEnsureExistByOrderStatus_NameAndUser_Id(String name, Long userId) {
        return repository.getOneByOrderStatus_NameAndUser_Id(name, userId).orElseThrow(() -> new ObjectNotFoundException("Order not found"));
    }

    @Override
    public Order findOneByUserIdOrCreate(Long id) {
        User userAuthenticated = getUserAuthenticated().getUser();

        return repository.getOneByOrderStatus_NameAndUser_Id("PENDING", id)
                .orElseGet(() -> create(userAuthenticated));
    }

    private Order update(Order order, OrderStatus orderStatus) {
        order.setOrderStatus(orderStatus);
        return repository.save(order);
    }

}
