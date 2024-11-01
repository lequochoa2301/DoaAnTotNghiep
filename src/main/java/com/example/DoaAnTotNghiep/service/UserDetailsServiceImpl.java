package com.example.DoaAnTotNghiep.service;

import com.example.DoaAnTotNghiep.configuration.MyUserDetails;
import com.example.DoaAnTotNghiep.entity.User;
import com.example.DoaAnTotNghiep.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.getUserByUsername(username);


        if (user == null) {

            throw new UsernameNotFoundException("Could not find user");
        }


        return new MyUserDetails(user);
    }
}