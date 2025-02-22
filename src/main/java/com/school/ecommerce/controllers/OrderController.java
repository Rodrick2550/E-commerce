package com.school.ecommerce.controllers;

import com.school.ecommerce.controllers.dtos.requests.UpdateOrderRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/order")
@RestController
public class OrderController {
    @Autowired
    private IOrderService service;

    @GetMapping
    public ResponseEntity<BaseResponse> list() {
        BaseResponse baseResponse = service.list();

        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable Long id) {
        BaseResponse response = service.get(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("user")
    public ResponseEntity<BaseResponse> getByUserId(@RequestParam(required = false) Long id) {
        BaseResponse response = service.getOrderByUserId(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("all/user")
    public ResponseEntity<BaseResponse> getDeliveredAndInProgressByUserId(@RequestParam(required = false) Long id) {
        BaseResponse response = service.getDeliveredAndInProgressOrderByUserId(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create() {
        BaseResponse response = service.create();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdateOrderRequest request, @PathVariable Long id) {
        BaseResponse response = service.update(id, request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id) {
        BaseResponse response = service.delete(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
