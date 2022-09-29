package uz.darico.feign;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.darico.exception.exception.UniversalException;

@Service
@RequiredArgsConstructor
public class WorkPlaceFeignService {
    private final RestTemplate restTemplate;

    public Long getUserID(Long workPlaceID) {
        return getUserIDRemote(workPlaceID);
//        return 100L;
    }

    public Long getUserIDRemote(Long ID) {
        try {
            ResponseEntity<Long> response = restTemplate.getForEntity("http://localhost:8080/kiruvchi/api/workplace/getUserID/" + ID, Long.class);
            if (response.getBody() == null) {
                throw new UniversalException("", HttpStatus.BAD_REQUEST);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new UniversalException("Remote server not work or %s ID workPlace not found".formatted(ID), HttpStatus.BAD_REQUEST);
        }
    }
}
