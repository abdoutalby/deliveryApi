package com.example.api.services;

 import com.example.api.models.Attachement;
import com.example.api.repos.AttachementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 import org.springframework.util.StringUtils;
 import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachemntServiceImpl implements AttachementService{
    @Autowired
    private AttachementRepo attachementRepo;
    @Override
    public Attachement save(MultipartFile file) throws Exception {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")){
                throw new Exception("Filename contains invalid characters");
            }
            Attachement attachement = new Attachement(fileName , file.getContentType() , file.getBytes());
            return attachementRepo.save(attachement);
        }catch (Exception e){
            throw new Exception("could not save file");
        }
    }

    @Override
    public Attachement getAttachement(Long signatureId) throws Exception {
        return attachementRepo.findById(signatureId)
                .orElseThrow(()->new Exception("no signature with this id"));
    }
}
