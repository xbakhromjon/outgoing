package uz.darico.feedback.feedback.dto;

import lombok.*;
import uz.darico.feedback.conf.dto.ConfFeedbackGetDTO;
import uz.darico.feedback.signatory.dto.SignatoryFeedbackGetDTO;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackGetDTO {
    private UUID rootMissiveID;
    private Long workPlaceID;
    private List<ConfFeedbackGetDTO> confFeedbacks;
    private List<SignatoryFeedbackGetDTO> signatoryFeedbacks;
}
