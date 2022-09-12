package com.example.api.repos;


import com.example.api.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UserRepo userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)   {
        log.info("Email : {}", username);
        User user = userRepository.findByEmail(username) ;
        System.out.println(user);
        return UserPrinciple.build(user);
    }


}