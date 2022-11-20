package uz.bakhromjon.inReceiver;

import org.springframework.stereotype.Service;
import uz.bakhromjon.base.entity.AbstractEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.inReceiver.dto.InReceiverCreateDTO;
import uz.bakhromjon.missive.Missive;

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
        List<UUID> IDs = inReceivers.stream().map(AbstractEntity::getId).collect(Collectors.toList());
        repository.deleteFromRelatedTable(IDs);
        repository.deleteAll(IDs);
    }

    public List<InReceiver> saveAll(List<InReceiver> inReceivers) {
        return repository.saveAll(inReceivers);
    }

    public List<InReceiver> getAllByMissiveID(UUID ID) {
        return repository.getAllByMissiveID(ID);
    }

    public void send(Missive missive) {

    }
}
