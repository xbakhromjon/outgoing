package uz.bakhromjon.signatory;

import org.springframework.stereotype.Component;
import uz.bakhromjon.feign.UserFeignService;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.feign.obj.UserInfo;
import uz.bakhromjon.signatory.dto.SignatoryGetDTO;

@Component
public class SignatoryMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final UserFeignService userFeignService;

    public SignatoryMapper(WorkPlaceFeignService workPlaceFeignService, UserFeignService userFeignService) {
        this.workPlaceFeignService = workPlaceFeignService;
        this.userFeignService = userFeignService;
    }

    public Signatory toEntity(Long signatoryWorkPlaceID) {
        Signatory signatory = new Signatory(signatoryWorkPlaceID,
                workPlaceFeignService.getUserID(signatoryWorkPlaceID),
                SignatoryStatus.SIGNED.getCode());
        return signatory;
    }

    public SignatoryGetDTO toGetDTO(Signatory signatory) {
        if (signatory == null) {
            return null;
        }
        UserInfo userInfo = userFeignService.getUserInfo(signatory.getUserID());
        return new SignatoryGetDTO(signatory.getId(), signatory.getWorkPlaceID(), userInfo.getFirstName(), userInfo.getLastName(), userInfo.getMiddleName(),
                SignatoryStatus.toSignatoryStatus(signatory.getStatusCode()), signatory.getStatusTime().toLocalDate());
    }
}
