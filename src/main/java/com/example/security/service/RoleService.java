package com.example.security.service;

import com.example.security.entity.Role;
import com.example.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repo;
    public Role getRole(Integer id){
        System.out.println(id +    "asdfghjkl;xcvbnm,.");
        System.out.println(repo.findAll());
        return repo.findById(id).get();
    }
}
