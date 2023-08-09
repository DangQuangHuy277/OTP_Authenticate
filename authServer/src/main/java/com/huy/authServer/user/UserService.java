package com.huy.authServer.user;

import com.huy.authServer.otp.Otp;
import com.huy.authServer.otp.OtpRepository;
import com.huy.authServer.user.util.GenerateCodeUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final Logger logger = Logger.getLogger(UserService.class.getName());

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void auth(User user) {
        User u = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Bad credentials."));
        if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            renewOtp(u);
        } else throw new BadCredentialsException("Bad credentials.");

    }

    private void renewOtp(User u) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> userOtp = otpRepository.findByUsername(u.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            otp.setCode(code);
        } else {
            Otp newOtp = new Otp(u.getUsername(), code);
            otpRepository.save(newOtp);
        }
        logger.info("The Otp for " + u.getUsername() + " is " + code);
    }

    public boolean check(Otp otpToValidate) {
        Optional<Otp> userOtp = otpRepository.findByUsername(otpToValidate.getUsername());
        return userOtp.isPresent() && userOtp.get().getCode().equals(otpToValidate.getCode());
    }
}
