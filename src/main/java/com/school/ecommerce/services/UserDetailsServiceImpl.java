package com.school.ecommerce.services;

import com.school.ecommerce.entities.Role;
import com.school.ecommerce.entities.User;
import com.school.ecommerce.repositories.IUserRepository;
import com.school.ecommerce.security.UserDetailsImpl;
import com.school.ecommerce.services.interfaces.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository repository;

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        List<Role> roles = userRoleService.getAllRolesByUserId(user.getId());

        return new UserDetailsImpl(user, getAuthorities(roles));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
