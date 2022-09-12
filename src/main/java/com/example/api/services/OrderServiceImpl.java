package com.example.api.services;

import com.example.api.models.Order;
import com.example.api.models.User;
import com.example.api.repos.OrderRepo;
import com.example.api.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    UserRepo userRepo;
    @Override
    public ResponseEntity<?> create(Order o) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        o.setDate(java.sql.Date.valueOf( format.format( new java.util.Date())));
        return ResponseEntity.ok().body(orderRepo.save(o));
    }

    @Override
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok().body(orderRepo.findAll());
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        return ResponseEntity.ok().body(orderRepo.findById(id));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        orderRepo.deleteById(id);
        return ResponseEntity.ok().body("done");
    }

    @Override
    public ResponseEntity<?> getAllByUser(Long uid) {
        Optional<User> user = userRepo.findById(uid);
        if (user.isPresent()){
            return  ResponseEntity.ok().body(orderRepo.findByUser(user.get()));
        }
        else{
            return ResponseEntity.ok().body("no user found ") ;
        }

    }

    @Override
    public ResponseEntity<?> getByDate(String date) {
      Date d=  java.sql.Date.valueOf(date);
        log.info("date transfered {}" , date);
        return ResponseEntity.ok().body(orderRepo.findByDate(d));
    }
}
