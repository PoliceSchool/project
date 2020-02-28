package com.policeschool.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DomainUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.policeschool.mall.domain.User user = userService.findByUserName(username);
        if (user != null) {
            return new User(user.getUserName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("用户[" + username + "]不存在");
        }
    }
}
