package uz.darico.auth;

/**
 * @author : Bakhromjon Khasanboyev
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.darico.auth.dto.LoginDTO;
import uz.darico.auth.dto.SignupDTO;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService service;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginRequest) {
        return service.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signUpRequest) {
        return service.registerUser(signUpRequest);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return service.logoutUser();
    }

    @GetMapping("/test")
    public String test() {
        return "hi";
    }
}
