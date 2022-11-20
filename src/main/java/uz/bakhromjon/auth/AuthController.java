package uz.bakhromjon.auth;

/**
 * @author : Bakhromjon Khasanboyev
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.bakhromjon.auth.dto.LoginDTO;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService service;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginRequest) {
        return service.authenticateUser(loginRequest);
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signUpRequest) {
//        return service.registerUser(signUpRequest);
//    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return service.logoutUser();
    }

}
