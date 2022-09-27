package uz.darico.feedback.conf;

import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.feedback.signatory.SignatoryFeedback;
import uz.darico.feedback.signatory.SignatoryFeedbackMapper;
import uz.darico.feedback.signatory.SignatoryFeedbackRepository;
import uz.darico.feedback.signatory.SignatoryFeedbackValidator;
import uz.darico.missive.dto.MissiveRejectDTO;
import uz.darico.sender.Sender;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConfFeedBackService extends AbstractService<ConfFeedbackRepository, ConfFeedbackValidator, ConfFeedbackMapper> {

    public ConfFeedBackService(ConfFeedbackRepository repository, ConfFeedbackValidator validator, ConfFeedbackMapper mapper) {
        super(repository, validator, mapper);
    }

    public ConfFeedback create(MissiveRejectDTO rejectDTO) {
        ConfFeedback confFeedback = new ConfFeedback(rejectDTO.getRejectedByUUID(), LocalDateTime.now(), rejectDTO.getMessage());
        return repository.save(confFeedback);
    }

    public void add(MissiveRejectDTO rejectDTO, Sender sender) {
        ConfFeedback confFeedback = create(rejectDTO);
        List<ConfFeedback> confFeedbacks = sender.getConfFeedbacks();
        confFeedbacks.add(confFeedback);
        sender.setConfFeedbacks(confFeedbacks);
    }
}
