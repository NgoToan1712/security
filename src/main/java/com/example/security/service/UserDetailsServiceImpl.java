package com.example.security.service;

import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUsersByEmail(email);
        if (user == null) {
            System.out.println("Email not found! " + email);
            throw new UsernameNotFoundException("Email " + email + " was not found in the database");
        }
        System.out.println("Found User :" + user);

        List<String> roleName = new ArrayList<>();
        for(Role role: user.getRoleList()){
            roleName.add(role.getRoleName());
        }
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleName != null) {
            for (String role : roleName) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantList);
        return userDetails;
    }
}
