package com.example.AddressBookApp.Service;
import com.example.AddressBookApp.DTO.AuthUserDTO;
import com.example.AddressBookApp.DTO.JwtReponse;
import com.example.AddressBookApp.DTO.LoginDTO;
import com.example.AddressBookApp.Exception.UserException;
import com.example.AddressBookApp.Interface.IAuthenticationService;
import com.example.AddressBookApp.Repository.AuthenticationRepository;
import com.example.AddressBookApp.Util.jwttoken;
import com.example.AddressBookApp.model.AuthUser;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthenticatiionService implements IAuthenticationService {

    @Autowired
    AuthenticationRepository authUserRepository;

    @Autowired
    jwttoken tokenUtil;

    @Autowired
    RabbitmqPubliser rabbitmqPublisher;  //  Inject RabbitMQ Publisher

    @Autowired
    RedisService redisTokenService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ===================== REGISTER USER =====================
    @Override
    public AuthUser register(AuthUserDTO userDTO) throws Exception {
        try {
            log.info("Registering new user: {}", userDTO.getEmail());
            AuthUser user = new AuthUser(userDTO);

            String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encryptedPassword);
            authUserRepository.save(user);

            String token = tokenUtil.createToken(user.getUserId());
            redisTokenService.saveToken(user.getUserId().toString(), token); // Store in Redis

            //  Publish event to RabbitMQ
            rabbitmqPublisher.sendMessage("userQueue", "New user registered: " + user.getEmail());

            log.info("User {} registered successfully.", user.getEmail());
            return user;
        } catch (Exception e) {
            log.error("Error during registration: {}", e.getMessage());
            throw new UserException("Registration failed. Please try again.");
        }
    }

    // ===================== LOGIN USER =====================
    @Override
    public String login(LoginDTO loginDTO, HttpServletResponse response) {
        try {
            log.info("Login attempt for email: {}", loginDTO.getEmail());
            Optional<AuthUser> user = Optional.ofNullable(authUserRepository.findByEmail(loginDTO.getEmail()));

            if (user.isPresent() && passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
                log.info("Login successful for user: {}", user.get().getEmail());

                String token = tokenUtil.createToken(user.get().getUserId()); // Generate JWT token
                redisTokenService.saveToken(user.get().getUserId().toString(), token);

               response.addHeader("Authorization", "Bearer : "+token);
                rabbitmqPublisher.sendMessage("loginQueue", "User logged in: " + user.get().getEmail());

                return "user logged in" + "\ntoken : " + token;   //
            } else {
                throw new UserException("Incorrect email or password.");
            }
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage());
            throw new UserException("Login failed due to an internal error.");
        }
    }


    // ===================== FORGOT PASSWORD =====================
    @Override
    @CacheEvict(value = "authTokens", key = "#email")
    public String forgotPassword(String email, String newPassword) {
        try {
            log.info("Processing forgot password request for email: {}", email);
            AuthUser user = authUserRepository.findByEmail(email);

            if (user == null) {
                throw new UserException("User not found with email: " + email);
            }

            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            authUserRepository.save(user);

            redisTokenService.deleteToken(user.getUserId().toString());

            //  Publish password reset event to RabbitMQ
            rabbitmqPublisher.sendMessage("passwordQueue", "Password reset for user: " + email);

            return "Password has been changed successfully!";
        } catch (Exception e) {
            log.error("Error updating password: {}", e.getMessage());
            throw new UserException("Password reset failed.");
        }
    }
    @Override
    @CacheEvict(value = "authTokens", key = "#email")
    public String resetPassword(String email, String currentPassword, String newPassword) {
        try {
            log.info("Resetting password for email: {}", email);
            AuthUser user = authUserRepository.findByEmail(email);

            if (user == null || !passwordEncoder.matches(currentPassword, user.getPassword())) {
                throw new UserException("Incorrect email or current password.");
            }

            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            authUserRepository.save(user);

            // Invalidate old token in Redis
            redisTokenService.deleteToken(user.getUserId().toString());

            // Publish password reset event to RabbitMQ
            rabbitmqPublisher.sendMessage("passwordQueue", "User reset password: " + email);

            return "Password reset successfully!";
        } catch (Exception e) {
            log.error("Error resetting password: {}", e.getMessage());
            throw new UserException("Password reset failed.");
        }
    }

}
