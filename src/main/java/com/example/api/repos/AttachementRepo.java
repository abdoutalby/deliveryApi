package com.example.api.repos;

import com.example.api.models.Attachement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachementRepo extends JpaRepository<Attachement , Long> {

}
