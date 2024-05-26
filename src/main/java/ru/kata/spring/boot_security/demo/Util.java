package ru.kata.spring.boot_security.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleSerivce;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Util {

    private final UserService userService;
    private final RoleSerivce roleSerivce;

    @Autowired
    public Util(UserService userService, RoleSerivce roleSerivce) {
        this.userService = userService;
        this.roleSerivce = roleSerivce;
    }

    @PostConstruct
    public void init() {
        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");
        roleSerivce.addRole(roleUser);
        roleSerivce.addRole(roleAdmin);

        userService.saveUser(new User("user", "user", "userovich", 27, "user@mail.com"),
                new HashSet<Role>(Set.of(roleAdmin, roleUser)));
        userService.saveUser(new User("user2", "user2", "usereovich2", 28, "user2@mail.com"),
                new HashSet<Role>(Set.of(roleUser)));
    }
}
