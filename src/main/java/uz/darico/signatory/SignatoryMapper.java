package uz.darico.signatory;

import org.springframework.stereotype.Component;
import uz.darico.feign.UserFeignService;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.feign.obj.UserInfo;
import uz.darico.sender.SenderStatus;
import uz.darico.sender.dto.SenderGetDTO;
import uz.darico.signatory.dto.SignatoryGetDTO;

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
