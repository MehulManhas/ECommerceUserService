package com.ecommerceproject.UserService.Service;

import com.ecommerceproject.UserService.Dto.UserDto;
import com.ecommerceproject.UserService.Exception.UserNotFoundException;
import com.ecommerceproject.UserService.Model.Role;
import com.ecommerceproject.UserService.Model.User;
import com.ecommerceproject.UserService.Repository.RoleRepository;
import com.ecommerceproject.UserService.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserDetails(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("Specified user with the given UserId doesn't exist");
        }

        return UserDto.from(userOptional.get());
    }

    public UserDto setUserRoles(Long userId, List<Long> roleIds){
        Optional<User> userOptional = userRepository.findById(userId);
        Set<Role> roles = roleRepository.findAllByUserIdIn(roleIds);

        if(userOptional.isEmpty()){
            return null;
        }

        User user = userOptional.get();
        user.setRoles(Set.copyOf(roles));

        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }
}
