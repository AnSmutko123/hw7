package com.stortor.hw7.servieces;

import com.stortor.hw7.entity.Authority;
import com.stortor.hw7.entity.Role;
import com.stortor.hw7.entity.User;
import com.stortor.hw7.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapAuthoritiesWithRoles(user.getRoles(), user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> mapAuthoritiesWithRoles(Collection<Role> roles, Collection<Authority> authorities) {
        List<String> roleNameList = roles.stream().map(r -> r.getName()).collect(Collectors.toList());
        List<String> authorityListFromRoles = roles.stream().map(role -> role.getAuthorities()).flatMap(auth -> auth.stream()).map(auth -> auth.getName()).collect(Collectors.toList());
        List<String> uniqAuthorities = authorities.stream().map(a -> a.getName()).collect(Collectors.toList());
        Set<String> unionSet = new HashSet<>();
        unionSet.addAll(roleNameList);
        unionSet.addAll(authorityListFromRoles);
        unionSet.addAll(uniqAuthorities);
        return unionSet.stream().map(a -> new SimpleGrantedAuthority(a)).collect(Collectors.toList());
    }

    public User save(User user) {
        userRepository.save(user);
        return user;
    }
}
