package com.dsmpear.main.service.user;

import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.entity.verifynumber.VerifyNumber;
import com.dsmpear.main.entity.verifynumber.VerifyNumberRepository;
import com.dsmpear.main.entity.verifyuser.VerifyUser;
import com.dsmpear.main.entity.verifyuser.VerifyUserRepository;
import com.dsmpear.main.exceptions.*;
import com.dsmpear.main.exceptions.InvalidEmailAddressException;
import com.dsmpear.main.exceptions.UserIsAlreadyRegisteredException;
import com.dsmpear.main.payload.request.EmailVerifyRequest;
import com.dsmpear.main.payload.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerifyNumberRepository numberRepository;
    private final VerifyUserRepository verifyUserRepository;

    @Override
    public void register(RegisterRequest request) {
        if (!request.isValidAddress("dsm.hs.kr"))
            throw new InvalidEmailAddressException();

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent())
            throw new UserIsAlreadyRegisteredException();

        verifyUserRepository.findByEmail(request.getEmail())
                .map(verifyUser -> {
                    return userRepository.save(
                            User.builder()
                                    .email(request.getEmail())
                                    .password(passwordEncoder.encode(request.getPassword()))
                                    .name(request.getName())
                                    .authStatus(true)
                                    .build()
                    );
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void verify(EmailVerifyRequest request) {
        numberRepository.findByEmail(request.getEmail())
                .filter(verifyNumber -> verifyNumber.verifyNumber(request.getNumber()))
                .map(verifyNumber -> verifyUserRepository.save(new VerifyUser(request.getEmail())))
                .orElseThrow(NumberNotFoundException::new);
    }
}