package uz.bakhromjon.template;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.base.controller.AbstractController;
import uz.bakhromjon.template.dto.TemplateCreateDTO;
import uz.bakhromjon.template.dto.TemplateUpdateDTO;

import java.util.UUID;

@RestController
@RequestMapping("/template")
public class TemplateController extends AbstractController<TemplateService> {
    public TemplateController(TemplateService service) {
        super(service);
    }
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody TemplateCreateDTO createDTO) {
        return service.create(createDTO);
    }


    @PatchMapping()
    public ResponseEntity<?> update(@RequestBody TemplateUpdateDTO updateDTO) {
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

    @GetMapping("/all/{workPlaceID}")
    public ResponseEntity<?> list(@PathVariable Long workPlaceID) {
        return service.list(workPlaceID);
    }

}
