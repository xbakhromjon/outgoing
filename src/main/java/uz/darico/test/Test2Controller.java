package uz.darico.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.darico.base.controller.AbstractController;

@RestController
@RequestMapping("/test")
public class Test2Controller extends AbstractController<Test2Service> {
    public Test2Controller(Test2Service service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Hello");
    }
}
