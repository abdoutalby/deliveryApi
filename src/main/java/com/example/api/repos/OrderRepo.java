package com.example.api.repos;

import com.example.api.models.Order;
import com.example.api.models.Role;
import com.example.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
    List<Order> findByDate(Date date);
}
