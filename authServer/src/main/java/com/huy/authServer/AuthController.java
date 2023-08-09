package com.huy.authServer;


import com.huy.authServer.otp.Otp;
import com.huy.authServer.user.User;
import com.huy.authServer.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/main")
    public String main() {
        return "Hello";
    }

    @PostMapping("user/add")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PostMapping("user/auth")
    public void auth(@RequestBody User user) {
        userService.auth(user);
    }

    @PostMapping("otp/check")
    public ResponseEntity<?> check(@RequestBody Otp otp) {
        if (userService.check(otp)) return ResponseEntity.ok().build();
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
