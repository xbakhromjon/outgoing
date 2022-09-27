package uz.darico.sender;

import org.springframework.stereotype.Component;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.base.mapper.BaseMapper;

@Component
public class SenderMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;

    public SenderMapper(WorkPlaceFeignService workPlaceFeignService) {
        this.workPlaceFeignService = workPlaceFeignService;
    }

    public Sender toEntity(Long workPlaceID) {
        return new Sender( workPlaceID,
                workPlaceFeignService.getUserID(workPlaceID),
                SenderStatus.PREPARING.getCode());
    }
}
