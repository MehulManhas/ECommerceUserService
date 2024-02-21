package com.ecommerceproject.UserService.Repository;

import com.ecommerceproject.UserService.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByUserIdIn(List<Long> roleIds);
}
