package uz.bakhromjon.feedback.signatory;

import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.missive.dto.MissiveRejectDTO;
import uz.bakhromjon.utils.BaseUtils;

import java.time.LocalDateTime;

@Service
public class SignatoryFeedBackService extends AbstractService<SignatoryFeedbackRepository, SignatoryFeedbackValidator, SignatoryFeedbackMapper> {
    private final BaseUtils baseUtils;

    public SignatoryFeedBackService(SignatoryFeedbackRepository repository, SignatoryFeedbackValidator validator, SignatoryFeedbackMapper mapper,
                                    BaseUtils baseUtils) {
        super(repository, validator, mapper);
        this.baseUtils = baseUtils;
    }

    public SignatoryFeedback create(MissiveRejectDTO rejectDTO) {
        SignatoryFeedback signatoryFeedback = new SignatoryFeedback(baseUtils.strToUUID(rejectDTO.getMissiveID()), rejectDTO.getRejectedWorkPlaceID(), LocalDateTime.now(), rejectDTO.getMessage());
        return repository.save(signatoryFeedback);
    }

}
