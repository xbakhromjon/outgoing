package uz.bakhromjon.organization;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.base.controller.AbstractController;
import uz.bakhromjon.organization.dto.OrganizationUpdateDTO;

import java.util.UUID;


/**
 * @author : Bakhromjon Khasanboyev
 **/

@RestController
@RequestMapping("/organization")
public class OrganizationController extends AbstractController<OrganizationService> {

    public OrganizationController(OrganizationService service) {
        super(service);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody OrganizationUpdateDTO updateDTO) {
        return service.update(updateDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(name = "id") UUID id) {
        return service.get(id);
    }

}

