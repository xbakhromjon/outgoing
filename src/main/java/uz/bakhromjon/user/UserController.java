package uz.bakhromjon.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.base.controller.AbstractController;
import uz.bakhromjon.contentFile.ContentFile;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.user.dto.UserCreateDTO;

import java.util.UUID;

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
    public ResponseEntity<?> create(@RequestBody UserCreateDTO createDTO) {
        return service.create(createDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam(name = "id") UUID id) {
        return service.delete(id);
    }
}
