package com.example.api.services;

import com.example.api.models.Order;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface OrderService {
    ResponseEntity<?> create(Order o) throws MessagingException, IOException;
    ResponseEntity<List<Order>> getAll();
    ResponseEntity<?> getById(Long id);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> getAllByUser(Long uid);

    ResponseEntity<?> getByDate(String date);
}
