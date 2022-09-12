package com.example.api.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Attachement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private String fileName ;
    private String fileType;
   @Lob
    private byte[] data;

    public Attachement(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
