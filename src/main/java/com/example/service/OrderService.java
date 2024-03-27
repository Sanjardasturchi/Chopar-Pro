package com.example.service;

import com.example.dto.OrderBucketDTO;
import com.example.dto.OrderDTO;
import com.example.dto.ProductDTO;
import com.example.dto.extra.order.OrderCreateDTO;
import com.example.dto.extra.order.OrderFilterDTO;
import com.example.dto.extra.order.ProductOrderDTO;
import com.example.entity.OrderBucketEntity;
import com.example.entity.OrderEntity;
import com.example.enums.OrderStatus;
import com.example.exp.AppBadException;
import com.example.repository.CustomOrderRepository;
import com.example.repository.OrderBucketRepository;
import com.example.repository.OrderRepository;
import com.example.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    //=============== Service =================
    @Autowired
    private ProductService productService;
    //=============== Repository ==============
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderBucketRepository orderBucketRepository;
    /**This repository use for filter orders*/
    @Autowired
    private CustomOrderRepository customOrderRepository;

    /**This method use for create new Order*/
    public String create(OrderCreateDTO order) {
        UUID profileId = SpringSecurityUtil.getCurrentUser().getId();
        Double amount = 0.0;
        for (ProductOrderDTO productOrderDTO : order.getProductList()) {
            amount += Double.valueOf(productOrderDTO.getAmount());
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAmount(amount);
        orderEntity.setStatus(OrderStatus.PREPARING);
        orderEntity.setCreatedDate(LocalDateTime.now());
        orderEntity.setDeliveredAddress(order.getDeliveredAddress());
        orderEntity.setDeliveredContact(order.getDeliveredContact());
        orderEntity.setVisible(true);
        orderEntity.setProfileId(profileId);
        orderRepository.save(orderEntity);
        for (ProductOrderDTO productOrderDTO : order.getProductList()) {
            ProductDTO productDTO = productService.getById(productOrderDTO.getId());
            if (productDTO != null) {
                OrderBucketEntity orderBucket = new OrderBucketEntity();
                orderBucket.setPrice(productDTO.getPrice());
                orderBucket.setAmount(Double.valueOf(productOrderDTO.getAmount()));
                orderBucket.setOrderId(orderEntity.getId());
                orderBucket.setProductId(productOrderDTO.getId());
                orderBucket.setVisible(true);
                orderBucket.setCreatedDate(LocalDateTime.now());
                orderBucketRepository.save(orderBucket);
            }
        }
        return "DONE";
    }
    /**This method use for update order status to Cancelled*/
    public String cancel(Integer id) {
        orderRepository.changeStatus(OrderStatus.CANCELLED, id);
        return "DONE";
    }
    /**This method use for cancel order by user*/
    public String cancelByUser(Integer id) {
        Optional<OrderEntity> optional = orderRepository.findById(id);
        if (optional.isEmpty()) {
            return "Order not found";
        }
        OrderEntity order = optional.get();
        UUID profileId = SpringSecurityUtil.getCurrentUser().getId();
        if (!order.getProfileId().equals(profileId)) {
            return "Order not yours";
        }
        if (!order.getStatus().equals(OrderStatus.PREPARING)) {
            return "You can not cancel order";
        }
        orderRepository.changeStatus(OrderStatus.CANCELLED, id);
        return "DONE";
    }
    /**This method use for update order status to delivered*/
    public String delivered(Integer id) {
        orderRepository.changeStatus(OrderStatus.DELIVERED, id);
        return "DONE";
    }
    /**This method use for update order status to cancelled*/
    public String cancelled(Integer id) {
        orderRepository.changeStatus(OrderStatus.CANCELLED, id);
        return "DONE";
    }
    /**This method use for update order status to on the way*/
    public String onTheWay(Integer id) {
        orderRepository.changeStatus(OrderStatus.ON_THE_WAY, id);
        return "DONE";
    }
    /**This method use for update order*/
    public String update(Integer id, OrderCreateDTO order) {
        Optional<OrderEntity> optional = orderRepository.findById(id);
        if (optional.isEmpty()) {
            return "Order not found";
        }

        Double amount = 0.0;
        for (ProductOrderDTO productOrderDTO : order.getProductList()) {
            amount += Double.valueOf(productOrderDTO.getAmount());
        }
        OrderEntity orderEntity = optional.get();
        if (!orderEntity.getStatus().equals(OrderStatus.PREPARING)) {
            return "You can not cancel order";
        }
        orderEntity.setAmount(amount);
        orderEntity.setDeliveredAddress(order.getDeliveredAddress());
        orderEntity.setDeliveredContact(order.getDeliveredContact());
        orderRepository.save(orderEntity);
        orderBucketRepository.deleteByOrderId(orderEntity.getId());
        for (ProductOrderDTO productOrderDTO : order.getProductList()) {
            ProductDTO productDTO = productService.getById(productOrderDTO.getId());
            if (productDTO != null) {
                OrderBucketEntity orderBucket = new OrderBucketEntity();
                orderBucket.setPrice(productDTO.getPrice());
                orderBucket.setAmount(Double.valueOf(productOrderDTO.getAmount()));
                orderBucket.setOrderId(orderEntity.getId());
                orderBucket.setProductId(productOrderDTO.getId());
                orderBucket.setVisible(true);
                orderBucket.setCreatedDate(LocalDateTime.now());
                orderBucketRepository.save(orderBucket);
            }
        }
        return "DONE";
    }
    /**This method use for get order by id*/
    public OrderDTO getById(Integer id) {
        Optional<OrderEntity> optional = orderRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Not found");
        }
        OrderDTO orderDTO = new OrderDTO();
        OrderEntity orderEntity = optional.get();
        orderDTO.setId(orderEntity.getId());
        orderDTO.setAmount(orderDTO.getAmount());
        orderDTO.setDeliveredContact(orderEntity.getDeliveredContact());
        orderDTO.setDeliveredAddress(orderEntity.getDeliveredAddress());
        orderDTO.setStatus(orderEntity.getStatus());
        orderDTO.setCreatedDate(orderEntity.getCreatedDate());
        orderDTO.setProfileId(orderEntity.getProfileId());
        List<OrderBucketDTO> productList = new LinkedList<>();
        Iterable<OrderBucketEntity> orderBucketEntities = orderBucketRepository.findByOrderId(orderEntity.getId());
        for (OrderBucketEntity orderBucketEntity : orderBucketEntities) {
            OrderBucketDTO bucketDTO = new OrderBucketDTO();
            bucketDTO.setId(orderBucketEntity.getId());
            bucketDTO.setAmount(orderBucketEntity.getAmount());
            bucketDTO.setOrderId(orderBucketEntity.getOrderId());
            bucketDTO.setCreatedDate(orderBucketEntity.getCreatedDate());
            bucketDTO.setPrice(orderBucketEntity.getPrice());
            bucketDTO.setProductId(orderBucketEntity.getProductId());
            productList.add(bucketDTO);
        }
        orderDTO.setProductList(productList);
        return orderDTO;
    }
    /**This method use for get order by pagination*/
    public PageImpl<OrderDTO> getByPagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<OrderEntity> all = orderRepository.findAllByProfileId(SpringSecurityUtil.getCurrentUser().getId(),paging);
        List<OrderDTO> dtoList = new LinkedList<>();
        for (OrderEntity orderEntity : all.getContent()) {
            OrderDTO dto=new OrderDTO();
            dto.setId(orderEntity.getId());
            dto.setStatus(orderEntity.getStatus());
            dto.setAmount(orderEntity.getAmount());
            dto.setCreatedDate(orderEntity.getCreatedDate());
            dto.setDeliveredAddress(orderEntity.getDeliveredAddress());
            dto.setDeliveredContact(orderEntity.getDeliveredContact());
            dto.setProfileId(orderEntity.getProfileId());
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, paging, all.getTotalElements());
    }
    /**This method use for get order by pagination with filter*/
    public PageImpl<OrderDTO> getByPaginationWithFilter(OrderFilterDTO filterDTO, Integer page, Integer size) {
        PageImpl<OrderEntity> all = customOrderRepository.filter(filterDTO, size, page);
        List<OrderDTO> dtoList = new LinkedList<>();
        for (OrderEntity orderEntity : all.getContent()) {
            OrderDTO dto=new OrderDTO();
            dto.setId(orderEntity.getId());
            dto.setStatus(orderEntity.getStatus());
            dto.setAmount(orderEntity.getAmount());
            dto.setCreatedDate(orderEntity.getCreatedDate());
            dto.setDeliveredAddress(orderEntity.getDeliveredAddress());
            dto.setDeliveredContact(orderEntity.getDeliveredContact());
            dto.setProfileId(orderEntity.getProfileId());
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, PageRequest.of(page - 1, size), all.getTotalElements());
    }
}
