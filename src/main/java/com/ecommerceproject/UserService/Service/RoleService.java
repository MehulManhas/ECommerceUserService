package com.ecommerceproject.UserService.Service;

import com.ecommerceproject.UserService.Model.Role;
import com.ecommerceproject.UserService.Repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String name){
        Role role = new Role();
        role.setRoleName(name);

        return roleRepository.save(role);
    }

}
