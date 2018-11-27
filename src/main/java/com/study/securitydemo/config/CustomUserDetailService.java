package com.study.securitydemo.config;

import com.study.securitydemo.dao.UserDao;
import com.study.securitydemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = new User();
        user.setUsername(s);
        Example<User> of = Example.of(user);
        Optional<User> one = userDao.findOne(of);
        if(one.isPresent()){
            User user1 = one.get();
            return new org.springframework.security.core.userdetails.User(user1.getUsername(), user1.getPassword(), new ArrayList<>());
        }
        return null;
    }
}
