package uz.bakhromjon.fishka;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.base.controller.AbstractController;
import uz.bakhromjon.fishka.dto.FishkaCreateDTO;
import uz.bakhromjon.fishka.dto.FishkaUpdateDTO;

import java.util.UUID;

@RestController
@RequestMapping("/fishka")
public class FishkaController extends AbstractController<FishkaService> {

    public FishkaController(FishkaService service) {
        super(service);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody FishkaCreateDTO createDTO) {
        return service.create(createDTO);
    }


    @PatchMapping()
    public ResponseEntity<?> update(@RequestBody FishkaUpdateDTO updateDTO) {
        return service.update(updateDTO);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam(name = "ID") UUID ID) {
        return service.delete(ID);
    }

    @GetMapping("/{ID}")
    public ResponseEntity<?> get(@PathVariable(name = "ID") UUID ID) {
        return service.get(ID);
    }

    @GetMapping("/all/{orgID}")
    public ResponseEntity<?> list(@PathVariable Long orgID) {
        return service.list(orgID);
    }

}
