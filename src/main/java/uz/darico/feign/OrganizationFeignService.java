package uz.darico.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.darico.exception.exception.UniversalException;
import uz.darico.feign.obj.UserInfo;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 03/10/22, Mon, 17:22
 **/
@Service
@RequiredArgsConstructor
public class OrganizationFeignService {

    private final RestTemplate restTemplate;

    public String getName(Long ID) {
        return getNameRemote(ID);
//        return "Soliq";
    }

    public String getNameRemote(Long ID) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/kiruvchi/api/orgType/name/" + ID, String.class);
            if (response.getBody() == null) {
                throw new UniversalException("", HttpStatus.BAD_REQUEST);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new UniversalException("Remote server not work or %s ID organization not found".formatted(ID), HttpStatus.BAD_REQUEST);
        }
    }
}
