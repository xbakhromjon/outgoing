package uz.bakhromjon.department;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.base.controller.AbstractController;
import uz.bakhromjon.department.dto.DepartmentCreateDTO;
import uz.bakhromjon.department.dto.DepartmentUpdateDTO;

import java.util.UUID;


/**
 * @author : Bakhromjon Khasanboyev
 **/

@RestController
@RequestMapping("/department")
public class DepartmentController extends AbstractController<DepartmentService> {

    public DepartmentController(DepartmentService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DepartmentCreateDTO createDTO) {
        return service.create(createDTO);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody DepartmentUpdateDTO updateDTO) {
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

