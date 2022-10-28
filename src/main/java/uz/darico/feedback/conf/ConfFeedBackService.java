package uz.darico.feedback.conf;

import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.feedback.signatory.SignatoryFeedback;
import uz.darico.feedback.signatory.SignatoryFeedbackMapper;
import uz.darico.feedback.signatory.SignatoryFeedbackRepository;
import uz.darico.feedback.signatory.SignatoryFeedbackValidator;
import uz.darico.missive.dto.MissiveRejectDTO;
import uz.darico.sender.Sender;
import uz.darico.utils.BaseUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConfFeedBackService extends AbstractService<ConfFeedbackRepository, ConfFeedbackValidator, ConfFeedbackMapper> {

    private final BaseUtils baseUtils;

    public ConfFeedBackService(ConfFeedbackRepository repository, ConfFeedbackValidator validator, ConfFeedbackMapper mapper,
                               BaseUtils baseUtils) {
        super(repository, validator, mapper);
        this.baseUtils = baseUtils;
    }

    public ConfFeedback create(MissiveRejectDTO rejectDTO) {
        ConfFeedback confFeedback = new ConfFeedback(baseUtils.strToUUID(rejectDTO.getMissiveID()), rejectDTO.getWorkPlaceID(), LocalDateTime.now(), rejectDTO.getMessage());
        return repository.save(confFeedback);
    }
}
