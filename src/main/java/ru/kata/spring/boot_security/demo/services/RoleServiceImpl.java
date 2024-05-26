package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleSerivce {

    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Set<Role> findByRoleNameIn(List<String> roles) {
        return roleRepository.findByRoleNameIn(roles);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByRoleName(name);
    }
}
