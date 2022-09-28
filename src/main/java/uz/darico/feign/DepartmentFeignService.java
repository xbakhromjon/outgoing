package uz.darico.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.darico.exception.exception.UniversalException;

@Service
@RequiredArgsConstructor
public class DepartmentFeignService {
    private final RestTemplate restTemplate;

    public String getName(Long ID) {
        return "Soliq";
    }

    public String getNameRemote(Long ID) {
        try {
            return restTemplate.getForObject("http://213.230.125.86:80/kiruvchi/department/name" + ID, String.class);
        } catch (Exception e) {
            throw new UniversalException("Remote server not work", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
