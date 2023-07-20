package com.mojo.oauth2.service;

import com.mojo.oauth2.model.entity.User;
import com.mojo.oauth2.repo.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mojo
 * @description: 给oauth服务汇报用户表
 * @date 2023/7/19 0019 22:14
 */
@Service
public class UserService implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getPassword())
            );
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
