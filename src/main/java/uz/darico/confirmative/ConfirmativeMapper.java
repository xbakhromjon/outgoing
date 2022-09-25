package uz.darico.confirmative;

import org.springframework.stereotype.Component;
import uz.darico.workPlace.WorkPlaceFeignService;
import uz.darico.base.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.List;


@Component
public class ConfirmativeMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;

    public ConfirmativeMapper(WorkPlaceFeignService workPlaceFeignService) {
        this.workPlaceFeignService = workPlaceFeignService;
    }

    public List<Confirmative> toEntity(List<Long> confirmativeWorkPlaceIDs) {
        List<Confirmative> confirmatives = new ArrayList<>();
        for (int i = 0, confirmativeWorkPlaceIDsSize = confirmativeWorkPlaceIDs.size(); i < confirmativeWorkPlaceIDsSize; i++) {
            Long confirmativeWorkPlaceID = confirmativeWorkPlaceIDs.get(i);
            Confirmative confirmative = new Confirmative(confirmativeWorkPlaceID,
                    workPlaceFeignService.getUserID(confirmativeWorkPlaceID),
                    ConfirmativeStatus.NOT_VIEWED.getCode(), i + 1);
            confirmatives.add(confirmative);
        }
        return confirmatives;
    }
}
