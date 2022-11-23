package com.app.sueldoservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.sueldoservice.entity.UserData;
import com.app.sueldoservice.repository.UserDataRepository;

@Service
public class UserDataService {
    @Autowired
    UserDataRepository userDataRepository;

    public List<UserData> getAll() {
        return userDataRepository.findAll();
    }

    public UserData getUserById(Long id){
        return userDataRepository.findById(id).orElse(null);
    }

    public UserData saver(UserData userData){
        UserData userDataSaved = userDataRepository.save(userData);
        return userDataSaved;
    }
}
