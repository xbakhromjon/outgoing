package uz.bakhromjon.signatory;

import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.feign.obj.UserInfo;
import uz.bakhromjon.signatory.dto.SignatoryGetDTO;
import uz.bakhromjon.user.UserService;

import java.util.UUID;

@Component
public class SignatoryMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final UserService userService;

    public SignatoryMapper(WorkPlaceFeignService workPlaceFeignService, UserService userService) {
        this.workPlaceFeignService = workPlaceFeignService;
        this.userService = userService;
    }

    public Signatory toEntity(UUID signatoryWorkPlaceID) {
        Signatory signatory = new Signatory(signatoryWorkPlaceID,
                workPlaceFeignService.getUserID(signatoryWorkPlaceID),
                SignatoryStatus.SIGNED.getCode());
        return signatory;
    }

    public SignatoryGetDTO toGetDTO(Signatory signatory) {
        if (signatory == null) {
            return null;
        }
        UserInfo userInfo = userService.getUserInfo(signatory.getUserID());
        return new SignatoryGetDTO(signatory.getId(), signatory.getWorkPlaceID(), userInfo.getFirstName(), userInfo.getLastName(), userInfo.getMiddleName(),
                SignatoryStatus.toSignatoryStatus(signatory.getStatusCode()), signatory.getStatusTime().toLocalDate());
    }
}
