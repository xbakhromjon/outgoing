package uz.darico.feign;


import org.springframework.stereotype.Service;

@Service
public class WorkPlaceFeignService {
    public Long getUserID(Long workPlaceID) {
        return 100L;
    }
}
