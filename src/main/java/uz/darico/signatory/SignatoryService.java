package uz.darico.signatory;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.OutReceiverMapper;
import uz.darico.outReceiver.OutReceiverRepository;
import uz.darico.outReceiver.OutReceiverValidator;

import java.util.List;
import java.util.UUID;

@Service
public class SignatoryService extends AbstractService<SignatoryRepository, SignatoryValidator, SignatoryMapper> {

    public SignatoryService(SignatoryRepository repository, SignatoryValidator validator, SignatoryMapper mapper) {
        super(repository, validator, mapper);
    }

    public void delete(Signatory signatory) {
        repository.delete(signatory);
    }
}
