package uz.darico.feedback.conf;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.feedback.conf.dto.ConfFeedbackGetDTO;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.feign.obj.WorkPlaceShortInfo;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConfFeedbackMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;

    public List<ConfFeedbackGetDTO> toGetDTO(List<ConfFeedback> confFeedbacks) {
        List<ConfFeedbackGetDTO> confFeedbackGetDTOs = new ArrayList<>();
        for (ConfFeedback confFeedback : confFeedbacks) {
            WorkPlaceShortInfo workPlaceShortInfo = workPlaceFeignService.getWorkPlaceInfoRemote(confFeedback.getWorkPlaceID());
            ConfFeedbackGetDTO confFeedbackGetDTO = new ConfFeedbackGetDTO(confFeedback.getMissiveID(), workPlaceShortInfo.getFirstName(), workPlaceShortInfo.getLastName(),
                    workPlaceShortInfo.getMiddleName(), confFeedback.getRejectedAt(), confFeedback.getContent());
            confFeedbackGetDTOs.add(confFeedbackGetDTO);
        }
        return confFeedbackGetDTOs;
    }
}
