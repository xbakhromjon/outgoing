package uz.bakhromjon.workplace;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.base.controller.AbstractController;
import uz.bakhromjon.workplace.dto.WorkPlaceCreateDTO;
import uz.bakhromjon.workplace.dto.WorkPlaceUpdateDTO;

import java.util.UUID;


/**
 * @author : Bakhromjon Khasanboyev
 **/

@RestController
@RequestMapping("/work-place")
public class WorkPlaceController extends AbstractController<WorkPlaceService> {

    public WorkPlaceController(WorkPlaceService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody WorkPlaceCreateDTO createDTO) {
        return service.create(createDTO);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody WorkPlaceUpdateDTO updateDTO) {
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

