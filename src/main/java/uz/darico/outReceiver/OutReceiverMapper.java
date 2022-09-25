package uz.darico.outReceiver;

import org.springframework.stereotype.Component;
import uz.darico.workPlace.WorkPlaceFeignService;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;

import java.util.ArrayList;
import java.util.List;


@Component
public class OutReceiverMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;

    public OutReceiverMapper(WorkPlaceFeignService workPlaceFeignService) {
        this.workPlaceFeignService = workPlaceFeignService;
    }

    public List<OutReceiver> toEntity(List<OutReceiverCreateDTO> createDTOs) {
        List<OutReceiver> outReceivers = new ArrayList<>();
        for (OutReceiverCreateDTO createDTO : createDTOs) {
            OutReceiver outReceiver = new OutReceiver(createDTO.getCorrespondentID(),
                    createDTO.getCorrespondentEmail(), createDTO.getCorrespondentExat());
            outReceivers.add(outReceiver);
        }
        return outReceivers;
    }
}
