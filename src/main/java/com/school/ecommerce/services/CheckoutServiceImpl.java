package com.school.ecommerce.services;

import com.school.ecommerce.controllers.dtos.requests.CheckoutRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.Order;
import com.school.ecommerce.entities.OrderItem;
import com.school.ecommerce.entities.Product;
import com.school.ecommerce.services.interfaces.ICheckoutService;
import com.school.ecommerce.services.interfaces.IOrderService;
import com.school.ecommerce.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements ICheckoutService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductService productService;


    @Override
    public BaseResponse checkout(CheckoutRequest request) {

        Order order = orderService.findOneAndEnsureExistById(request.getOrderId());
        Float totalAmount = getTotalAmount(order);

        subtractStock(order);

        return BaseResponse.builder()
                .data(null)
                .message("Payment successful")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    private void subtractStock(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            Integer stockPurchased = orderItem.getQuantity();
            Integer newStock = product.getStock() - stockPurchased;

            productService.updateStock(product, newStock);
        }
    }

    private Float getTotalAmount(Order order) {
        Float total = 0.0F;
        for (OrderItem orderItem : order.getOrderItems()) {
            total += getTotalAmount(orderItem);
        }

        return total;
    }

    private Float getTotalAmount(OrderItem orderItem) {
        return orderItem.getProduct().getPrice() * orderItem.getQuantity();
    }
}
