package uz.darico.signatory;

import org.springframework.stereotype.Component;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.base.mapper.BaseMapper;

@Component
public class SignatoryMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;

    public SignatoryMapper(WorkPlaceFeignService workPlaceFeignService) {
        this.workPlaceFeignService = workPlaceFeignService;
    }

    public Signatory toEntity(Long signatoryWorkPlaceID) {
        Signatory signatory = new Signatory(signatoryWorkPlaceID,
                workPlaceFeignService.getUserID(signatoryWorkPlaceID),
                SignatoryStatus.SIGNED.getCode());
       return signatory;
    }
}
