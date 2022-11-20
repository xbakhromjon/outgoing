package uz.bakhromjon.feedback.feedback;

import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.feedback.conf.ConfFeedBackService;
import uz.bakhromjon.feedback.conf.ConfFeedback;
import uz.bakhromjon.feedback.feedback.dto.FeedbackGetDTO;
import uz.bakhromjon.feedback.signatory.SignatoryFeedBackService;
import uz.bakhromjon.feedback.signatory.SignatoryFeedback;
import uz.bakhromjon.missive.dto.MissiveRejectDTO;
import uz.bakhromjon.missive.enums.RejectType;
import uz.bakhromjon.utils.BaseUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedBackService extends AbstractService<FeedbackRepository, FeedbackValidator, FeedbackMapper> {

    private final BaseUtils baseUtils;
    private final ConfFeedBackService confFeedBackService;
    private final SignatoryFeedBackService signatoryFeedBackService;

    public FeedBackService(FeedbackRepository repository, FeedbackValidator validator, FeedbackMapper mapper,
                           BaseUtils baseUtils, ConfFeedBackService confFeedBackService,
                           SignatoryFeedBackService signatoryFeedBackService) {
        super(repository, validator, mapper);
        this.baseUtils = baseUtils;
        this.confFeedBackService = confFeedBackService;
        this.signatoryFeedBackService = signatoryFeedBackService;
    }

    public Feedback create(MissiveRejectDTO rejectDTO, Long workPlaceID) {
        UUID rootMissiveID = baseUtils.strToUUID(rejectDTO.getRootMissiveID());
        Feedback feedback = new Feedback(rootMissiveID, workPlaceID);
        return repository.save(feedback);
    }

    public void add(MissiveRejectDTO rejectDTO, Long workPlaceID) {
        UUID rootMissiveID = baseUtils.strToUUID(rejectDTO.getRootMissiveID());
        Feedback feedback = getPersist(rootMissiveID, workPlaceID);
        if (feedback == null) {
            feedback = create(rejectDTO, workPlaceID);
        }
        if (rejectDTO.getRejectType().equals(RejectType.CONFIRMATIVE.getCode())) {
            List<ConfFeedback> confFeedbacks = feedback.getConfFeedbacks();
            ConfFeedback newConfFeedback = confFeedBackService.create(rejectDTO);
            confFeedbacks.add(newConfFeedback);
            feedback.setConfFeedbacks(confFeedbacks);
        } else {
            List<SignatoryFeedback> signatoryFeedbacks = feedback.getSignatoryFeedbacks();
            SignatoryFeedback newSignatoryFeedback = signatoryFeedBackService.create(rejectDTO);
            signatoryFeedbacks.add(newSignatoryFeedback);
            feedback.setSignatoryFeedbacks(signatoryFeedbacks);
        }
        repository.save(feedback);
    }

    public Feedback getPersist(UUID rootMissiveID, Long workPlaceID) {
        Optional<Feedback> optional = repository.find(rootMissiveID, workPlaceID);
        return optional.orElse(null);
    }


    public FeedbackGetDTO getFeedbackDTO(UUID rootVersionID, Long workPlaceID, UUID missiveID) {
        Feedback persist = getPersist(rootVersionID, workPlaceID);
        if (persist == null) {
            return null;
        }
        return mapper.toGetDTO(persist, missiveID);
    }

    public FeedbackGetDTO getFeedbackDTO(UUID rootVersionID, Long workPlaceID, Long filterWorkPlaceID) {
        Feedback persist = getPersist(rootVersionID, workPlaceID);
        if (persist == null) {
            return null;
        }
        return mapper.toGetDTO(persist, filterWorkPlaceID);
    }
}
