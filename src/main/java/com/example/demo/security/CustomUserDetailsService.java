package com.example.demo.security;

import com.example.demo.db.models.CustomUser;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    //@Qualifier("userService")
    @Autowired
    public CustomUserDetailsService(UserService userService) { // можно ли так делать???
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        CustomUser user = userService.getUserByLogin(login);
        System.out.println("User: " + user);
        return user;
    }

//    private List<GrantedAuthority> getGrantedAuthorities(CustomUser user) { //Не используется!!!
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add((new SimpleGrantedAuthority("ROLE_" + user.getAuthorities())));
//        System.out.println("authorities: " + authorities);
//        return authorities;
//    }

}
