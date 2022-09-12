package com.example.api.controllers;

import com.example.api.models.Attachement;
import com.example.api.models.Order;
import com.example.api.services.AttachemntServiceImpl;
import com.example.api.services.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;

@Transactional
@RestController
@RequestMapping("api/orders/")
@Slf4j
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    AttachemntServiceImpl attachemntService;

    @PostMapping()
    public ResponseEntity<?> add(@RequestPart("order") Order order, @RequestPart("File") MultipartFile file) throws Exception {
        Attachement attachement = attachemntService.save(file);
        String downloadUrl = "";
        downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/orders/getSignature/")
                .path(attachement.getId().toString())
                .toUriString();
        order.setSignature(downloadUrl);
        return ResponseEntity.ok().body(orderService.create(order));
    }
    @GetMapping("getSignature/{id}")
    public ResponseEntity<Resource> downloadSignature(@PathVariable("id")Long signatureId) throws Exception {
       Attachement attachement = null ;

       attachement = attachemntService.getAttachement(signatureId);

     return   ResponseEntity.ok()
             .contentType(MediaType.parseMediaType(attachement.getFileType()))
             .header(HttpHeaders.CONTENT_DISPOSITION ,
                     "attachement; filename=\""+attachement.getFileName()+"\""
                     )
             .body(new ByteArrayResource(attachement.getData()));
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {

        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return orderService.delete(id);
    }

    @GetMapping("/user/{uid}")
    public ResponseEntity<?> getByUid(@PathVariable("uid") Long uid) {
        return orderService.getAllByUser(uid);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> getByDate(@PathVariable("date") String date) {
        return orderService.getByDate(date);
    }

}
