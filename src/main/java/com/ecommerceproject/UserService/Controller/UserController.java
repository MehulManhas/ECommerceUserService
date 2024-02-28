package com.ecommerceproject.UserService.Controller;

import com.ecommerceproject.UserService.Dto.UserDto;
import com.ecommerceproject.UserService.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId){
        UserDto userDto = userService.getUserDetails(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody List<Long> roleIds){
        UserDto userDto = userService.setUserRoles(userId,roleIds);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
