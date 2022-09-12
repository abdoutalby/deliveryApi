package com.example.api.services;

import com.example.api.models.Attachement;
import org.springframework.web.multipart.MultipartFile;

public interface AttachementService {
    Attachement save(MultipartFile file) throws Exception;

    Attachement getAttachement(Long signatureId) throws Exception;
}
