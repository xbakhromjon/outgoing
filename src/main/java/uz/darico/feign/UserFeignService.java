package uz.darico.feign;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.darico.exception.exception.UniversalException;
import uz.darico.feign.obj.UserInfo;

@Service
@RequiredArgsConstructor
public class UserFeignService {
    private final RestTemplate restTemplate;

    public UserInfo getUserInfo(Long ID) {
        if (ID == null) {
            return null;
        }
        return getUserInfoRemote(ID);
//        return new UserInfo("Bakhromjon", "Khasanboyev", "Soxibjon o'g'li");
    }

    public UserInfo getUserInfoRemote(Long ID) {
        try {
            ResponseEntity<UserInfo> response = restTemplate.getForEntity("http://192.168.30.151:8080/kiruvchi/api/user/info/" + ID, UserInfo.class);
//            ResponseEntity<UserInfo> response = restTemplate.getForEntity("http://localhost:8080/kiruvchi/api/user/info/" + ID, UserInfo.class);
            return response.getBody();
        } catch (Exception e) {
            throw new UniversalException("Remote server not work or %s ID user not found".formatted(ID), HttpStatus.BAD_REQUEST);
        }
    }
}
