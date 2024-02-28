package com.ecommerceproject.UserService.Service;

import com.ecommerceproject.UserService.Dto.UserDto;
import com.ecommerceproject.UserService.Exception.InvalidCredentialException;
import com.ecommerceproject.UserService.Exception.InvalidSessionLogoutException;
import com.ecommerceproject.UserService.Exception.UserNotFoundException;
import com.ecommerceproject.UserService.Mapper.UserEntityDtoMapper;
import com.ecommerceproject.UserService.Model.Session;
import com.ecommerceproject.UserService.Model.SessionStatus;
import com.ecommerceproject.UserService.Model.User;
import com.ecommerceproject.UserService.Repository.SessionRepository;
import com.ecommerceproject.UserService.Repository.UserRepository;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;


@Service
public class AuthService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<UserDto> login(String email, String password){
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found in the database");
        }

        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new InvalidCredentialException("password is incorrect for the given id");
        }

        //String token = RandomStringUtils.randomAlphanumeric(30);

        MacAlgorithm macAlgorithm = Jwts.SIG.HS256;
        SecretKey key = macAlgorithm.key().build();

        Map<String, Object> jsonForJwt = new HashMap<>();
        jsonForJwt.put("email", user.getEmail());
        jsonForJwt.put("roles", user.getRoles());
        jsonForJwt.put("createdAt", new Date(LocalDate.now().toEpochDay()));
        jsonForJwt.put("expiringAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String token = Jwts.builder().claims(jsonForJwt).signWith(key, macAlgorithm).compact();

        Session session = new Session();
        session.setToken(token);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setUserId(user.getUserId());
        session.setExpiringAt(new Date());
        session.setLogInAt(new Date());

        sessionRepository.save(session);

        UserDto responseUserDto = UserEntityDtoMapper.getUserDtoFromUserEntity(user);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());

        headers.add(HttpHeaders.SET_COOKIE,token);

        return new ResponseEntity<>(responseUserDto, headers, HttpStatus.OK);
    }

    public ResponseEntity<Void> logout(String token, Long userId){
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUserId(token, userId);

        if(sessionOptional.isEmpty()){
            throw new InvalidSessionLogoutException("Session you're trying to log out doesn't exist");
        }

        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public UserDto signUp(String email, String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public SessionStatus validate(Long userId, String token){
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUserId(token, userId);

        if(sessionOptional.isEmpty() || sessionOptional.get().getSessionStatus().equals(SessionStatus.ENDED)){
            throw new UserNotFoundException("Invalid token for the given user id");
        }


        Jwt parsed = Jwts.parser().build().parse(token);

        return SessionStatus.ACTIVE;
    }

    //Below methods are used just for testing purpose not to be deployed

    public List<Session> getAllSessions(){
        List<Session> sessionList = sessionRepository.findAll();
        return sessionList;
    }

}
