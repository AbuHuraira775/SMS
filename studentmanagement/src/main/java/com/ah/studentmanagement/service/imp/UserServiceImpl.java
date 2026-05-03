package com.ah.studentmanagement.service.imp;

import com.ah.studentmanagement.entity.Users;
import com.ah.studentmanagement.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author "Abu Huraira"
 **/



@Service
public class UserServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid username"));
        return User.withUsername(username)
                .password(users.getPassword())
                .disabled(!users.getActive())
                .build();
    }
}
