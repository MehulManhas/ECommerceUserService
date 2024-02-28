package com.ecommerceproject.UserService.Controller;

import com.ecommerceproject.UserService.Dto.CreateRoleRequestDto;
import com.ecommerceproject.UserService.Model.Role;
import com.ecommerceproject.UserService.Service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto requestDto){
        Role role = roleService.createRole(requestDto.getName());

        return new ResponseEntity<>(role, HttpStatus.OK);
    }

}
