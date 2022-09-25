package uz.darico.outReceiver;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.confirmative.ConfirmativeRepository;
import uz.darico.confirmative.ConfirmativeValidator;

import java.util.List;
import java.util.UUID;

@Service
public class OutReceiverService extends AbstractService<OutReceiverRepository, OutReceiverValidator, OutReceiverMapper> {
    public OutReceiverService(OutReceiverRepository repository, OutReceiverValidator validator, OutReceiverMapper mapper) {
        super(repository, validator, mapper);
    }

    public void deleteAll(List<OutReceiver> outReceivers) {
        List<UUID> IDs = outReceivers.stream().map(AbstractEntity::getId).toList();
//        repository.deleteAll(IDs);
    }
}
