package com.spring.rest.service;

import com.spring.rest.model.Role;
import com.spring.rest.model.User;
import com.spring.rest.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()){
            throw new IllegalArgumentException("User must have at least a role set!");
        }

        for(Role role : user.getRoles()){
            if (!role.getName().startsWith("ROLE_")){
                role.setName("ROLE_" + role.getName());
            }

            if (role.getUser() == null){
                role.setUser(user);
            }
        }

        repository.save(user);
    }


}
