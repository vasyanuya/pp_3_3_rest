package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleSerivce roleSerivce;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleSerivce roleSerivce, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleSerivce = roleSerivce;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    @Transactional
    public boolean saveUser(User user) {

        if (user.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        }
        user.setRoles(user.getRoles().stream()
                .map(role -> roleSerivce.getByName(role.getRoleName()))
                .collect(Collectors.toSet()));

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean saveUser(User user, Set<Role> roles) {
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public List<User> getListAllUsers() {
        return userRepository.findAll();
    }

    @Transactional()
    public User findUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User());
    }

    @Transactional
    public void updateUser(User user) {

        User userFromDB = userRepository.findUserById(user.getId());
        if (user.getRoles().isEmpty()) {
            user.setRoles(userFromDB.getRoles());
        } else {
            user.setRoles(user.getRoles().stream()
                    .map(role -> roleSerivce.getByName(role.getRoleName()))
                    .collect(Collectors.toSet()));
        }

        if (!user.getPassword().equals(userFromDB.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}