package uz.darico.feign;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.darico.exception.exception.UniversalException;
import uz.darico.feign.obj.UserInfo;

@Service
@RequiredArgsConstructor
public class UserFeignService {
    private final RestTemplate restTemplate;
    public UserInfo getUserInfo(Long ID) {
        return new UserInfo("Bakhromjon", "Khasanboyev", "Soxibjon o'g'li");
    }

    public UserInfo getUserInfoRemote(Long ID) {
        try {
            return restTemplate.getForObject("http://213.230.125.86:80/kiruvchi/user/" + ID, UserInfo.class);
        } catch (Exception e) {
            throw new UniversalException("Remote server not work", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
