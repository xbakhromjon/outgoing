package uz.darico.feign;

import org.springframework.stereotype.Service;

@Service
public class DepartmentFeignService {

    public String getName(Long ID) {
        return "Soliq";
    }
}
