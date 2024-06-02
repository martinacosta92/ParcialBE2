package com.elaparatoadmin.controller;


import com.elaparatoadmin.model.User;
import com.elaparatoadmin.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_app-reponedor') and hasRole('ROLE_app-vendedor')")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<List<User>> findByUserName(@PathVariable String userName) {
        return ResponseEntity.ok(service.findByUserName(userName));
    }




}
