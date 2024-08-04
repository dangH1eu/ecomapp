package com.project.ecomapp.serviceimpl;

import com.project.ecomapp.dto.CartItemDTO;
import com.project.ecomapp.dto.OrderDTO;
import com.project.ecomapp.exception.DataNotFoundException;
import com.project.ecomapp.model.*;
import com.project.ecomapp.repository.OrderDetailRepository;
import com.project.ecomapp.repository.OrderRepository;
import com.project.ecomapp.repository.ProductRepository;
import com.project.ecomapp.repository.UserRepository;
import com.project.ecomapp.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, ModelMapper modelMapper, ProductRepository productRepository, OrderDetailRepository orderDetailRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        // check user id
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("cannot find user with id: " + orderDTO.getUserId()));
        // convert orderDTO -> order
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        // update order field from orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);

        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now(): orderDTO.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("date must be at least today");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);

        // create list of orderDetails from cartItems
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(CartItemDTO cartItemDTO : orderDTO.getCartItems()){
            // create orderDetail from CartItemDTO
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            // get product info from cartItemDTO
            Long productId = cartItemDTO.getProductId();
            int quantity = cartItemDTO.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("cannot find product with id: " + productId));


            // add info to orderDetail
            orderDetail.setProduct(product);
            orderDetail.setNumberOfProducts(quantity);

            orderDetail.setPrice(product.getPrice());

            // add orderDetail to list
            orderDetails.add(orderDetail);
        }
        // save list of orderDetails to db
        orderDetailRepository.saveAll(orderDetails);
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("cannot find order with id: " + id));
        User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(() ->
                new DataNotFoundException("cannot find user with id: " + id));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO,order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }


    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
