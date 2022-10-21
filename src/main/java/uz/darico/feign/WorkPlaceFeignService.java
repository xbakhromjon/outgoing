package uz.darico.feign;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.darico.exception.exception.UniversalException;
import uz.darico.feign.obj.UserRole;
import uz.darico.feign.obj.WorkPlaceShortInfo;

import java.util.List;

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
            ResponseEntity<Long> response = restTemplate.getForEntity("http://192.168.30.151:8080/kiruvchi/api/workplace/getUserID/" + ID, Long.class);
//            ResponseEntity<Long> response = restTemplate.getForEntity("http://localhost:8080/kiruvchi/api/workplace/getUserID/" + ID, Long.class);
            if (response.getBody() == null) {
                throw new UniversalException("", HttpStatus.BAD_REQUEST);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new UniversalException(String.format("Remote server not work or %s ID workPlace not found", ID), HttpStatus.BAD_REQUEST);
        }
    }

    public Long getOrgID(Long workPlaceID) {
        try {
            ResponseEntity<Long> response = restTemplate.getForEntity("http://192.168.30.151:8080/kiruvchi/api/workplace/getUserID/" + workPlaceID, Long.class);
//            ResponseEntity<Long> response = restTemplate.getForEntity("http://localhost:8080/kiruvchi/api/workplace/getUserID/" + workPlaceID, Long.class);
            if (response.getBody() == null) {
                throw new UniversalException("", HttpStatus.BAD_REQUEST);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new UniversalException(String.format("Remote server not work or %s workPlaceID workPlace not found", workPlaceID), HttpStatus.BAD_REQUEST);
        }
    }


    public WorkPlaceShortInfo getWorkPlaceInfoRemote(Long workPlaceID) {
        try {
            ResponseEntity<WorkPlaceShortInfo> response = restTemplate.getForEntity("http://192.168.30.151:8080/kiruvchi/api/workplace/info/" + workPlaceID, WorkPlaceShortInfo.class);
//            ResponseEntity<WorkPlaceShortInfo> response = restTemplate.getForEntity("http://localhost:8080/kiruvchi/api/workplace/info/" + workPlaceID, WorkPlaceShortInfo.class);
            return response.getBody();
        } catch (Exception e) {
            throw new UniversalException(String.format("Remote server not work or %s ID workPlace not found", workPlaceID), HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isOfficeManager(Long workPlaceID) {
        WorkPlaceShortInfo workPlaceInfoRemote = getWorkPlaceInfoRemote(workPlaceID);
        List<UserRole> roles = workPlaceInfoRemote.getRoles();
        for (UserRole role : roles) {
            if (role.getRank() == 8) {
                return true;
            }
        }
        return false;
    }

}
