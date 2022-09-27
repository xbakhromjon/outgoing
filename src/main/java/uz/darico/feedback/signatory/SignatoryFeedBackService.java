package uz.darico.feedback.signatory;

import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.missive.dto.MissiveRejectDTO;
import uz.darico.sender.Sender;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SignatoryFeedBackService extends AbstractService<SignatoryFeedbackRepository, SignatoryFeedbackValidator, SignatoryFeedbackMapper> {
    public SignatoryFeedBackService(SignatoryFeedbackRepository repository, SignatoryFeedbackValidator validator, SignatoryFeedbackMapper mapper) {
        super(repository, validator, mapper);
    }

    public SignatoryFeedback create(MissiveRejectDTO rejectDTO) {
        SignatoryFeedback signatoryFeedback = new SignatoryFeedback(rejectDTO.getRejectedByUUID(), LocalDateTime.now(), rejectDTO.getMessage());
        return repository.save(signatoryFeedback);
    }

    public void add(MissiveRejectDTO rejectDTO, Sender sender) {
        SignatoryFeedback signatoryFeedback = create(rejectDTO);
        List<SignatoryFeedback> signatoryFeedbacks = sender.getSignatoryFeedbacks();
        signatoryFeedbacks.add(signatoryFeedback);
        sender.setSignatoryFeedbacks(signatoryFeedbacks);
    }
}
