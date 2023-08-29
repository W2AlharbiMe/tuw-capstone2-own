package com.example.capstone2.Service;

import com.example.capstone2.Model.User;
import com.example.capstone2.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findUserByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("username or password is invalid.");
        }

        return user;
    }
}
