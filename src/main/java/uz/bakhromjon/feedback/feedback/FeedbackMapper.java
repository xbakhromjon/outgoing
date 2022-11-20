package uz.bakhromjon.feedback.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.feedback.conf.ConfFeedback;
import uz.bakhromjon.feedback.conf.ConfFeedbackMapper;
import uz.bakhromjon.feedback.conf.dto.ConfFeedbackGetDTO;
import uz.bakhromjon.feedback.feedback.dto.FeedbackGetDTO;
import uz.bakhromjon.feedback.signatory.SignatoryFeedback;
import uz.bakhromjon.feedback.signatory.SignatoryFeedbackMapper;
import uz.bakhromjon.feedback.signatory.dto.SignatoryFeedbackGetDTO;

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
