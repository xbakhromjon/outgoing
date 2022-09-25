package uz.darico.inReceiver;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;

import java.util.List;
import java.util.UUID;
import uz.darico.base.service.AbstractService;
@Service
public class InReceiverService extends AbstractService<InReceiverRepository, InReceiverValidator, InReceiverMapper> {

    public InReceiverService(InReceiverRepository repository, InReceiverValidator validator, InReceiverMapper mapper) {
        super(repository, validator, mapper);
    }

    public void deleteAll(List<InReceiver> inReceivers) {
        List<UUID> IDs = inReceivers.stream().map(AbstractEntity::getId).toList();
//        repository.deleteAll(IDs);
    }
}
