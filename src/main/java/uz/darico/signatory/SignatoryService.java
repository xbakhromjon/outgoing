package uz.darico.signatory;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.OutReceiverMapper;
import uz.darico.outReceiver.OutReceiverRepository;
import uz.darico.outReceiver.OutReceiverValidator;
import uz.darico.sender.Sender;
import uz.darico.workPlace.WorkPlaceFeignService;

import java.util.List;
import java.util.UUID;

@Service
public class SignatoryService extends AbstractService<SignatoryRepository, SignatoryValidator, SignatoryMapper> {

    private final WorkPlaceFeignService workPlaceFeignService;

    public SignatoryService(SignatoryRepository repository, SignatoryValidator validator,
                            SignatoryMapper mapper, WorkPlaceFeignService workPlaceFeignService) {
        super(repository, validator, mapper);
        this.workPlaceFeignService = workPlaceFeignService;
    }

    public Signatory save(Signatory signatory) {
        return repository.save(signatory);
    }
    public void edit(Signatory signatory, Long newWorkPlaceID) {
        if (!newWorkPlaceID.equals(signatory.getWorkPlaceID())) {
            Long signatoryUserID = workPlaceFeignService.getUserID(newWorkPlaceID);
            signatory.setWorkPlaceID(newWorkPlaceID);
            signatory.setUserID(signatoryUserID);
            repository.save(signatory);
        }
    }

    public boolean existsByID(UUID rejectedBy) {
        return repository.existsById(rejectedBy);
    }

    public void notReady(UUID id) {

    }
}
