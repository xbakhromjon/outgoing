package uz.darico.feign;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.darico.exception.exception.UniversalException;

@Service
@RequiredArgsConstructor
public class WorkPlaceFeignService {
    private final RestTemplate restTemplate;
    public Long getUserID(Long workPlaceID) {
        return 100L;
    }

    public Long getUserIDRemote(Long ID) {
        try {
            return restTemplate.getForObject("http://213.230.125.86:80/kiruvchi/workPlace/getUserID/" + ID , Long.class);
        } catch (Exception e) {
            throw new UniversalException("Remote server not work", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
