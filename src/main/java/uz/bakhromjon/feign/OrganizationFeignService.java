package uz.bakhromjon.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.utils.OrgShortInfo;

import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 03/10/22, Mon, 17:22
 **/
@Service
@RequiredArgsConstructor
public class OrganizationFeignService {

    private final RestTemplate restTemplate;

    public OrgShortInfo getShortInfo(UUID ID) {
//        return getNameRemote(ID);
        return new OrgShortInfo("Soliq", "soliq@gmail.com");
    }

    public OrgShortInfo getNameRemote(Long ID) {
        try {
//            ResponseEntity<OrgShortInfo> response = restTemplate.getForEntity("http://192.168.30.112:8080/kiruvchi/api/orgType/shortInfo/" + ID, OrgShortInfo.class);
            ResponseEntity<OrgShortInfo> response = restTemplate.getForEntity("http://localhost:8081/kiruvchi/api/orgType/shortInfo/" + ID, OrgShortInfo.class);
            if (response.getBody() == null) {
                throw new UniversalException("", HttpStatus.BAD_REQUEST);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new UniversalException(String.format("Remote server not work or %s ID organization not found", ID), HttpStatus.BAD_REQUEST);
        }
    }
}
