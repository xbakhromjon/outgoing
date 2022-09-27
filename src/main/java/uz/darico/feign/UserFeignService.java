package uz.darico.feign;

import org.springframework.stereotype.Service;
import uz.darico.feign.obj.UserInfo;

@Service
public class UserFeignService {
    public UserInfo getUserInfo(Long ID) {
        return new UserInfo("Bakhromjon", "Khasanboyev", "Soxibjon o'g'li");
    }
}
