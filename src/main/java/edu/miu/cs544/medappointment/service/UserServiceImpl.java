package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long getAuthUserId(String username) {
        Long id = null;
        User user = userRepository.findByUsername(username);
        if(user!=null) id = user.getId();
        return id;
    }

    @Override
    public User getAuthUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        return userRepository.findByUsername(username);
    }
}
