package com.elves.dscatalog.services;


import com.elves.dscatalog.model.Role;
import com.elves.dscatalog.model.User;
import com.elves.dscatalog.projections.UserDetailsProjection;
import com.elves.dscatalog.repositories.UserRepository;
import com.elves.dscatalog.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws DomainException {

        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.isEmpty()) {
            throw new DomainException("Email not found");
        }

        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());
        result.forEach(projection -> user.addRole(new Role(projection.getRoleId(), projection.getAuthority())) );

        return user;
    }

}
