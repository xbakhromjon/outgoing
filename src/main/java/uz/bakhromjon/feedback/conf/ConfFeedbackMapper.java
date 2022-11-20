package uz.bakhromjon.feedback.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.feedback.conf.dto.ConfFeedbackGetDTO;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.feign.obj.WorkPlaceShortInfo;

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
