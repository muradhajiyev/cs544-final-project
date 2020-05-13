package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Role;
import edu.miu.cs544.medappointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        edu.miu.cs544.medappointment.entity.User user = userRepository.findByEmailOrUsername(username, username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        return new User(username, user.getPassword(), authorities);
    }


    private List<GrantedAuthority> buildUserAuthority(Set<Role> roles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // add user's authorities
        for (Role userRole : roles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
        }

        return new ArrayList<GrantedAuthority>(setAuths);
    }
}
