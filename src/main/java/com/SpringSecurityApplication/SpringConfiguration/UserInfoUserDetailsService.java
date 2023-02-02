package com.SpringSecurityApplication.SpringConfiguration;

import com.SpringSecurityApplication.models.UserModel;
import com.SpringSecurityApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userModel = repository.findByName(username);
//        System.out.println("hi:"+userModel+":hi"+username);
        return userModel.map(UserInfoUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("user not found "+username));
    }
}
