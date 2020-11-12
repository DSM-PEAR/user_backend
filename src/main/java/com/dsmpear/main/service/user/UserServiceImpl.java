package com.dsmpear.main.service.user;

import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.InvalidEmailAddressException;
import com.dsmpear.main.exceptions.UserIsAlreadyRegisteredException;
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

    @Override
    public void register(RegisterRequest request) {
        if (!request.isValidAddress("dsm.hs.kr"))
            throw new InvalidEmailAddressException();

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent())
            throw new UserIsAlreadyRegisteredException();
        userRepository.save(
                User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .authStatus(false)
                    .build()
        );
    }
}
