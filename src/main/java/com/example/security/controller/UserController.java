package com.example.security.controller;

import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.service.RoleService;
import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    public UserService service;
    @Autowired
    private RoleService roleService;
    @GetMapping
    public List<User> getList(){
        return service.getList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id){
        try{
            User user=service.getById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{roleID}")
    public void addUser(@PathVariable Integer roleID,@RequestBody User user){
        System.out.println(roleID);
        service.add(roleID,user);
    }

@GetMapping("/role/{id}")
    public Role getRole(@PathVariable("id") Integer id){
        return roleService.getRole(id);
}
}
