package uz.bakhromjon.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.bakhromjon.base.controller.AbstractController;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<UserService> {
    public UserController(UserService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<?> create() {
        return null;
    }
}
