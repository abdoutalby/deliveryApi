package com.example.api.services;

import com.example.api.models.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<?> create(Order o);
    ResponseEntity<List<Order>> getAll();
    ResponseEntity<?> getById(Long id);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> getAllByUser(Long uid);

    ResponseEntity<?> getByDate(String date);
}
