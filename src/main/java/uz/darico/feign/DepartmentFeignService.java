package uz.darico.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.darico.exception.exception.UniversalException;

@Service
@RequiredArgsConstructor
public class DepartmentFeignService {
    private final RestTemplate restTemplate;

    public String getName(Long ID) {
//        return getNameRemote(ID);
        return "Soliq";
    }

    public String getNameRemote(Long ID) {
        try {
//            ResponseEntity<String> response = restTemplate.getForEntity("http://192.168.30.112:8080/kiruvchi/api/department/getdepartmentUzName/" + ID, String.class);
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8081/kiruvchi/api/department/getdepartmentUzName/" + ID, String.class);
            if (response.getBody() == null) {
                throw new UniversalException("", HttpStatus.BAD_REQUEST);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new UniversalException(String.format("Remote server not work or %s ID department not found", ID), HttpStatus.BAD_REQUEST);
        }
    }
}
