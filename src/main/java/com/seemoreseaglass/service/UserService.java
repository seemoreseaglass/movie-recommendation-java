package com.seemoreseaglass.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.seemoreseaglass.repository.UserRepository;
import com.seemoreseaglass.entity.User;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.seemoreseaglass.entity.User user =
            repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }


    public void register(String username, String rawPassword) {
        if (repo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("そのユーザー名はすでに使われています");
        }

        String hashed = new BCryptPasswordEncoder().encode(rawPassword);
        repo.save(new User(null, username, hashed));
    }
}