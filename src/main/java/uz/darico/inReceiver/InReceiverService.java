package uz.darico.inReceiver;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;

import java.util.List;
import java.util.UUID;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.Confirmative;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;

@Service
public class InReceiverService extends AbstractService<InReceiverRepository, InReceiverValidator, InReceiverMapper> {

    public InReceiverService(InReceiverRepository repository, InReceiverValidator validator, InReceiverMapper mapper) {
        super(repository, validator, mapper);
    }

    public List<InReceiver> refresh(List<InReceiverCreateDTO> inReceiverCreateDTOs, List<InReceiver> trashInReceivers) {
        List<InReceiver> newInReceivers = create(inReceiverCreateDTOs);
        deleteAll(trashInReceivers);
        return newInReceivers;
    }


    public List<InReceiver> create(List<InReceiverCreateDTO> inReceiverCreateDTOs) {
        List<InReceiver> inReceivers = mapper.toEntity(inReceiverCreateDTOs);
        return repository.saveAll(inReceivers);
    }

    public void deleteAll(List<InReceiver> inReceivers) {
        List<UUID> IDs = inReceivers.stream().map(AbstractEntity::getId).toList();
        repository.deleteFromRelatedTable(IDs);
        repository.deleteAll(IDs);
    }

    public List<InReceiver> saveAll(List<InReceiver> inReceivers) {
        return repository.saveAll(inReceivers);
    }
}
