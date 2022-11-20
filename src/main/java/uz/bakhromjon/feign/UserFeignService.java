package uz.bakhromjon.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.feign.obj.UserInfo;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFeignService {
    private final RestTemplate restTemplate;

    public UserInfo getUserInfo(UUID ID) {
        if (ID == null) {
            return null;
        }
//        return getUserInfoRemote(ID);
        return new UserInfo("Bakhromjon", "Khasanboyev", "Soxibjon o'g'li");
    }

    public UserInfo getUserInfoRemote(Long ID) {
        try {
//            ResponseEntity<UserInfo> response = restTemplate.getForEntity("http://192.168.30.112:8080/kiruvchi/api/user/info/" + ID, UserInfo.class);
            ResponseEntity<UserInfo> response = restTemplate.getForEntity("http://localhost:8081/kiruvchi/api/user/info/" + ID, UserInfo.class);
            return response.getBody();
        } catch (Exception e) {
            throw new UniversalException(String.format("Remote server not work or %s ID user not found", ID), HttpStatus.BAD_REQUEST);
        }
    }
}
