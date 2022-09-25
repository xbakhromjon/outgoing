package uz.darico.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.darico.base.controller.AbstractController;
import uz.darico.test.dto.TestUpdateDTO;
@RestController
@RequestMapping("/test")
public class TestController extends AbstractController<TestService> {

    public TestController(TestService service) {
        super(service);
    }

    @PatchMapping()
    public ResponseEntity<?> update(@RequestBody TestUpdateDTO testUpdateDTO) {
        return service.update(testUpdateDTO);
    }
}
