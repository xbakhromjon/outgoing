package uz.darico.missive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.darico.base.controller.AbstractController;
import uz.darico.missive.dto.MissiveCreateDTO;
import uz.darico.missive.dto.MissiveUpdateDTO;

@RestController
@RequestMapping("/missive")
public class MissiveController extends AbstractController<MissiveService> {
    public MissiveController(MissiveService service) {
        super(service);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody MissiveCreateDTO createDTO) {
        return service.create(createDTO);
    }


    @PatchMapping()
    public ResponseEntity<?> update(@RequestBody MissiveUpdateDTO updateDTO) {
        return service.update(updateDTO);
    }

}
