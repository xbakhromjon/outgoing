package uz.darico.feedback.signatory;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.base.validator.BaseValidator;
import uz.darico.feedback.conf.ConfFeedback;
import uz.darico.feedback.conf.dto.ConfFeedbackGetDTO;
import uz.darico.feedback.signatory.dto.SignatoryFeedbackGetDTO;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.feign.obj.WorkPlaceShortInfo;

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
