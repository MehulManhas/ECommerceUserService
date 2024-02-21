package com.ecommerceproject.UserService.Repository;

import com.ecommerceproject.UserService.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String emailId);
}
