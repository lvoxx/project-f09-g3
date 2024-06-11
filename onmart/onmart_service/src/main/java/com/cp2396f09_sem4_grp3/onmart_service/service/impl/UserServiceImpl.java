package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.RegistrationRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.UpdateUserRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.UserRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.UserResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.PasswordResetToken;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_common.entities.VerificationToken;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.InvalidResetPasswordTokenException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.UserAlreadyExistsException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.UserNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.PasswordResetTokenRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.UserRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.VerificationTokenRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.PasswordResetTokenService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    @Value("${app.messages.user.service.valid}")
    private String validTokenMess;

    @Value("${app.messages.user.service.invalid}")
    private String invalidTokenMess;

    @Value("${app.messages.user.service.expired}")
    private String expiredTokenMess;

    @Value("${app.jpa.max-items-in-page}")
    private int maxItems;

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    private PasswordResetTokenService passwordResetService; // WARNING: Dont make this to final, may cause cycle
                                                            // dependency
    private final ModelMapper modelMapper;

    @Autowired
    public void setPasswordResetService(@Lazy PasswordResetTokenService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No user found."));
    }

    @Override
    public User addUserByGuest(RegistrationRequest request) {
        Optional<User> user = userRepository.findByEmail(request.email());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException(
                    "User with email " + request.email() + " already exists");
        }
        var newUser = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .password(passwordEncoder.encode(request.password()))
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found."));
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = VerificationToken
                .builder()
                .token(token)
                .user(theUser)
                .build();
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        Optional<VerificationToken> tokenData = tokenRepository.findByToken(theToken);
        if (!tokenData.isPresent()) {
            return this.invalidTokenMess;
        }
        VerificationToken token = tokenData.get();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return this.expiredTokenMess;
        }
        User user = token.getUser();
        user.setEnabled(true);

        userRepository.save(user);

        return this.validTokenMess;
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken).get();
        var tokenExpirationTime = VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .build();

        verificationToken.setExpirationTime(tokenExpirationTime.getTokenExpirationTime());

        return tokenRepository.save(verificationToken);
    }

    @Override
    public UserDetailsService userDetailsFromUser() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Can not find user with email: " + email));
    }

    @Override
    public void changePassword(User theUser, String newPassword) {
        theUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(theUser);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetService.validatePasswordResetToken(token);
    }

    @Override
    public User findUserByPasswordToken(String token) {
        return passwordResetService.findUserByPasswordToken(token)
                .orElseThrow(() -> new InvalidResetPasswordTokenException("Can not find user with token request"));
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        PasswordResetToken newToken = user.getPasswordResetToken();
        if (newToken == null) {
            passwordResetTokenRepository.save(
                    PasswordResetToken.builder()
                            .token(passwordToken)
                            .user(user)
                            .build());
        } else {
            // Set new reset password token
            newToken.setToken(passwordToken);
            newToken.setExpirationTime(PasswordResetToken.getTokenExpirationTime());
            passwordResetTokenRepository.save(newToken);
        }
    }

    @Override
    public boolean oldPasswordIsValid(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public Page<UserResponse> listAllUsers(Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, maxItems, Sort.by(sortBy));

        return userRepository.findAll(paging)
                .map(u -> modelMapper.map(u, UserResponse.class));
    }

    @Override
    public UserResponse getUserById(Long id) {
        return modelMapper.map(this.findById(id), UserResponse.class);
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        Optional<User> existUser = userRepository.findByEmail(request.getEmail());
        if (existUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .isEnabled(request.isEnabled())
                .build();

        return modelMapper.map(userRepository.saveAndFlush(user), UserResponse.class);
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request, Long id) {
        User oldUser = findById(id);

        oldUser.setFirstName(request.getFirstName());
        oldUser.setLastName(request.getLastName());
        oldUser.setPhone(request.getPhone());
        oldUser.setEnabled(request.isEnabled());
        oldUser.setRole(request.getRole());

        return modelMapper.map(userRepository.saveAndFlush(oldUser), UserResponse.class);
    }

    @Override
    public void lockUserById(Long id) {
        userRepository.trashedUserById(id);
    }

    @Override
    public void unlockUserById(Long id) {
        userRepository.untrashedUserById(id);
    }

    @Override
    public void addLoyaltyPoints(BigDecimal points, Long id) {
        userRepository.updateLoyaltyPoints(points, id);
    }

}