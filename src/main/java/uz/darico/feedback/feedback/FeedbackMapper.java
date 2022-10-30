package uz.darico.feedback.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.feedback.conf.ConfFeedBackService;
import uz.darico.feedback.conf.ConfFeedback;
import uz.darico.feedback.conf.ConfFeedbackMapper;
import uz.darico.feedback.conf.dto.ConfFeedbackGetDTO;
import uz.darico.feedback.feedback.dto.FeedbackGetDTO;
import uz.darico.feedback.signatory.SignatoryFeedBackService;
import uz.darico.feedback.signatory.SignatoryFeedback;
import uz.darico.feedback.signatory.SignatoryFeedbackMapper;
import uz.darico.feedback.signatory.dto.SignatoryFeedbackGetDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class FeedbackMapper implements BaseMapper {
    private final ConfFeedbackMapper confFeedbackMapper;
    private final SignatoryFeedbackMapper signatoryFeedbackMapper;

    public FeedbackGetDTO toGetDTO(Feedback feedback, UUID missiveID) {
        List<ConfFeedback> confFeedbacks = feedback.getConfFeedbacks().stream().filter(item -> item.getMissiveID().equals(missiveID)).collect(Collectors.toList());
        List<ConfFeedbackGetDTO> confFeedbackGetDTOs = confFeedbackMapper.toGetDTO(confFeedbacks);
        List<SignatoryFeedback> signatoryFeedbacks = feedback.getSignatoryFeedbacks().stream().filter(item -> item.getMissiveID().equals(missiveID)).collect(Collectors.toList());
        List<SignatoryFeedbackGetDTO> signatoryFeedbackGetDTOs = signatoryFeedbackMapper.toGetDTO(signatoryFeedbacks);
        return new FeedbackGetDTO(feedback.getRootMissiveID(), feedback.getWorkPlaceID(), confFeedbackGetDTOs, signatoryFeedbackGetDTOs);
    }

    public FeedbackGetDTO toGetDTO(Feedback feedback, Long filterWorkPlaceID) {
        List<ConfFeedback> confFeedbacks = feedback.getConfFeedbacks().stream().filter(item -> item.getWorkPlaceID().equals(filterWorkPlaceID)).collect(Collectors.toList());
        List<ConfFeedbackGetDTO> confFeedbackGetDTOs = confFeedbackMapper.toGetDTO(confFeedbacks);
        List<SignatoryFeedback> signatoryFeedbacks = feedback.getSignatoryFeedbacks().stream().filter(item -> item.getWorkPlaceID().equals(filterWorkPlaceID)).collect(Collectors.toList());
        List<SignatoryFeedbackGetDTO> signatoryFeedbackGetDTOs = signatoryFeedbackMapper.toGetDTO(signatoryFeedbacks);
        return new FeedbackGetDTO(feedback.getRootMissiveID(), feedback.getWorkPlaceID(), confFeedbackGetDTOs, signatoryFeedbackGetDTOs);
    }
}
