package com.elaparatoadmin.service;

import com.elaparatoadmin.model.User;
import com.elaparatoadmin.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private IUserRepository repository;

    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public List<User> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

}
