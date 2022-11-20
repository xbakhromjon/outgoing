package uz.bakhromjon.feedback.conf;

import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.missive.dto.MissiveRejectDTO;
import uz.bakhromjon.utils.BaseUtils;

import java.time.LocalDateTime;

@Service
public class ConfFeedBackService extends AbstractService<ConfFeedbackRepository, ConfFeedbackValidator, ConfFeedbackMapper> {

    private final BaseUtils baseUtils;

    public ConfFeedBackService(ConfFeedbackRepository repository, ConfFeedbackValidator validator, ConfFeedbackMapper mapper,
                               BaseUtils baseUtils) {
        super(repository, validator, mapper);
        this.baseUtils = baseUtils;
    }

    public ConfFeedback create(MissiveRejectDTO rejectDTO) {
        ConfFeedback confFeedback = new ConfFeedback(baseUtils.strToUUID(rejectDTO.getMissiveID()), rejectDTO.getRejectedWorkPlaceID(), LocalDateTime.now(), rejectDTO.getMessage());
        return repository.save(confFeedback);
    }
}
