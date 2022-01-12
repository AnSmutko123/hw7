package com.stortor.hw7.servieces;

import com.stortor.hw7.configs.SecurityConfig;
import com.stortor.hw7.entity.Role;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.repositories.UserRepository;
import com.stortor.hw7.validators.RegisterUserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RegisterUserValidator userValidator;
    private final RoleService roleService;
    private final SecurityConfig securityConfig;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User createNewUser(User user) {
        boolean usernameIsEmpty = userRepository.findByUsername(user.getUsername()).isEmpty();
        boolean emailIsEmpty = userRepository.findByEmail(user.getEmail()).isEmpty();
        userValidator.validate(user, usernameIsEmpty, emailIsEmpty);
        String pass = securityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(pass);
        Role roleUser = roleService.findRoleByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void updateRoles(Long id, Collection<Role> roles) {
        User user = userRepository.getById(id);
        user.setRoles(roles);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
