package com.example.security.service;

import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository repository;

    public List<User> getList() {
        return repo.findAll();
    }

    public User getByEmail(String email) {
        return repo.findUsersByEmail(email);
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }

    public void addRole(Integer roleId, Integer userId){
        Role role = repository.findById(roleId).get();
        User user=repo.findById(userId).get();
        user.getRoleList().add(role);
        repo.save(user);
    }
    public void add(Integer roleId, User user){
        Role role=repository.findById(roleId).get();
        System.out.println(role);
        user.getRoleList().add(role);
        repo.save(user);
    }

    public User getById(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

}
