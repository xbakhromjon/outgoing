package uz.bakhromjon.userPosition;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.base.controller.AbstractController;
import uz.bakhromjon.userPosition.dto.UserPositionCreateDTO;
import uz.bakhromjon.userPosition.dto.UserPositionUpdateDTO;

import java.util.UUID;


/**
 * @author : Bakhromjon Khasanboyev
 **/

@RestController
@RequestMapping("/user-position")
public class UserPositionController extends AbstractController<UserPositionService> {

    public UserPositionController(UserPositionService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserPositionCreateDTO createDTO) {
        return service.create(createDTO);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserPositionUpdateDTO updateDTO) {
        return service.update(updateDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(name = "id") UUID id) {
        return service.get(id);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam(name = "id") UUID id) {
        return service.delete(id);
    }
}

