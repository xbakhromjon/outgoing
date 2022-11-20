package uz.bakhromjon.feedback.signatory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.feedback.signatory.dto.SignatoryFeedbackGetDTO;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.feign.obj.WorkPlaceShortInfo;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SignatoryFeedbackMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;

    public List<SignatoryFeedbackGetDTO> toGetDTO(List<SignatoryFeedback> signatoryFeedbacks) {
        List<SignatoryFeedbackGetDTO> signatoryFeedBackGetDTOs = new ArrayList<>();
        for (SignatoryFeedback signatoryFeedback : signatoryFeedbacks) {
            WorkPlaceShortInfo workPlaceShortInfo = workPlaceFeignService.getWorkPlaceInfoRemote(signatoryFeedback.getWorkPlaceID());
            SignatoryFeedbackGetDTO signatoryFeedbackGetDTO = new SignatoryFeedbackGetDTO(signatoryFeedback.getMissiveID(), workPlaceShortInfo.getFirstName(), workPlaceShortInfo.getLastName(),
                    workPlaceShortInfo.getMiddleName(), signatoryFeedback.getRejectedAt(), signatoryFeedback.getContent());
            signatoryFeedBackGetDTOs.add(signatoryFeedbackGetDTO);
        }
        return signatoryFeedBackGetDTOs;
    }
}
