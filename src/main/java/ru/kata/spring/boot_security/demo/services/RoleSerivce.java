package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;
import java.util.Set;

public interface RoleSerivce {

    public void addRole(Role role);

    public List<Role> findAll();

    public Set<Role> findByRoleNameIn(List<String> roles);

    public Role getByName(String name);

}
