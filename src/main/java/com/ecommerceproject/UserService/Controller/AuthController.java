package com.ecommerceproject.UserService.Controller;

import com.ecommerceproject.UserService.Dto.LogInRequestDto;
import com.ecommerceproject.UserService.Dto.SignUpRequestDto;
import com.ecommerceproject.UserService.Dto.UserDto;
import com.ecommerceproject.UserService.Dto.ValidateTokenRequestDto;
import com.ecommerceproject.UserService.Model.Session;
import com.ecommerceproject.UserService.Model.SessionStatus;
import com.ecommerceproject.UserService.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LogInRequestDto logInRequestDto){
        return authService.login(logInRequestDto.getEmail(), logInRequestDto.getPassword());
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> logout(@PathVariable("id") Long userId, @RequestHeader String token){
        return authService.logout(token, userId);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto signUpRequestDto){
        UserDto userDto = authService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

//    @GetMapping("/auth/session")
//    public ResponseEntity<>

    @GetMapping("/validate")
    public ResponseEntity<Void> validate(@RequestBody ValidateTokenRequestDto validateTokenRequestDto){
        SessionStatus sessionStatus = authService.validate(validateTokenRequestDto.getUserId(), validateTokenRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Below APIs are used for testing purpose only not to be deployed

    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getAllSessions(){
        List<Session> sessionList = authService.getAllSessions();
        return new ResponseEntity<>(sessionList, HttpStatus.OK);
    }

}
