package com.ecommerceproject.UserService.Repository;

import com.ecommerceproject.UserService.Model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByTokenAndUserId(String token, long userId);
}
