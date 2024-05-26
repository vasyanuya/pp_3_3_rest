package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Set;


public interface UserService extends UserDetailsService {

    public UserDetails loadUserByUsername(String username);

    public boolean saveUser(User user);


    public boolean saveUser(User user, Set<Role> roles);

    public List<User> getListAllUsers();

    public User findUserById(int id);

    public void updateUser(User user);

    public void deleteUser(int id);
}
