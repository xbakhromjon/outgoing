package uz.darico.inReceiver;

import org.springframework.stereotype.Component;
import uz.darico.workPlace.WorkPlaceFeignService;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;


import java.util.ArrayList;
import java.util.List;


@Component
public class InReceiverMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;

    public InReceiverMapper(WorkPlaceFeignService workPlaceFeignService) {
        this.workPlaceFeignService = workPlaceFeignService;
    }

    public List<InReceiver> toEntity(List<InReceiverCreateDTO> createDTOs) {
        List<InReceiver> inReceivers = new ArrayList<>();
        for (InReceiverCreateDTO createDTO : createDTOs) {
            InReceiver inReceiver = new InReceiver(createDTO.getCorrespondentID());
            inReceivers.add(inReceiver);
        }
        return inReceivers;
    }
}
