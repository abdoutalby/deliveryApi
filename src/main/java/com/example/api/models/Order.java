package com.example.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "orders")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id ;
    private String customerName ;
    private  String jobNo;
    private  String contactPerson ;
    private  String contactNo;
    private String size ;
    private String timeIn;
    private  String timeOut;
    private  String vehiculeNo;
    private String type ;
    private  int qty;
    private String  driverName;
    private String receiverName;
    private String  signature;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date ;
    @ManyToOne
    private User user;
}
