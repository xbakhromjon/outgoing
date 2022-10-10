package uz.darico.fishka;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.darico.base.controller.AbstractController;
import uz.darico.fishka.dto.FishkaCreateDTO;
import uz.darico.fishka.dto.FishkaUpdateDTO;
import uz.darico.template.TemplateService;
import uz.darico.template.dto.TemplateCreateDTO;
import uz.darico.template.dto.TemplateUpdateDTO;

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
