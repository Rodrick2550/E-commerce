package com.school.ecommerce.controllers;


import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.services.interfaces.IOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/order-status")
@RestController
public class OrderStatusController {
    @Autowired
    private IOrderStatusService service;

    @GetMapping
    ResponseEntity<BaseResponse> list() {
        BaseResponse baseResponse = service.list();

        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }
}
