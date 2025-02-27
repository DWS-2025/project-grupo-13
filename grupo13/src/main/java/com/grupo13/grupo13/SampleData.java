package com.grupo13.grupo13;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class SampleData {
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        User lupe = new User(100, "Lupe");
        userRepository.save(lupe);
    }

    
}
