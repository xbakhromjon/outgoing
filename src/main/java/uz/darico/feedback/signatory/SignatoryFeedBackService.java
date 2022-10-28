package uz.darico.feedback.signatory;

import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.missive.dto.MissiveRejectDTO;
import uz.darico.sender.Sender;
import uz.darico.utils.BaseUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SignatoryFeedBackService extends AbstractService<SignatoryFeedbackRepository, SignatoryFeedbackValidator, SignatoryFeedbackMapper> {
    private final BaseUtils baseUtils;

    public SignatoryFeedBackService(SignatoryFeedbackRepository repository, SignatoryFeedbackValidator validator, SignatoryFeedbackMapper mapper,
                                    BaseUtils baseUtils) {
        super(repository, validator, mapper);
        this.baseUtils = baseUtils;
    }

    public SignatoryFeedback create(MissiveRejectDTO rejectDTO) {
        SignatoryFeedback signatoryFeedback = new SignatoryFeedback(baseUtils.strToUUID(rejectDTO.getMissiveID()), rejectDTO.getWorkPlaceID(), LocalDateTime.now(), rejectDTO.getMessage());
        return repository.save(signatoryFeedback);
    }

}
