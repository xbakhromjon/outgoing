package uz.darico.outReceiver;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.confirmative.ConfirmativeRepository;
import uz.darico.confirmative.ConfirmativeValidator;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;

import java.util.List;
import java.util.UUID;

@Service
public class OutReceiverService extends AbstractService<OutReceiverRepository, OutReceiverValidator, OutReceiverMapper> {
    public OutReceiverService(OutReceiverRepository repository, OutReceiverValidator validator, OutReceiverMapper mapper) {
        super(repository, validator, mapper);
    }

    public List<OutReceiver> refresh(List<OutReceiverCreateDTO> outReceiverCreateDTOs, List<OutReceiver> trashOutReceivers) {
        List<OutReceiver> newOutReceivers = create(outReceiverCreateDTOs);
        deleteAll(trashOutReceivers);
        return newOutReceivers;
    }


    public List<OutReceiver> create(List<OutReceiverCreateDTO> outReceiverCreateDTOs) {
        List<OutReceiver> outReceivers = mapper.toEntity(outReceiverCreateDTOs);
        return repository.saveAll(outReceivers);
    }

    public void deleteAll(List<OutReceiver> outReceivers) {
        List<UUID> IDs = outReceivers.stream().map(AbstractEntity::getId).toList();
        repository.deleteFromRelatedTable(IDs);
        repository.deleteAll(IDs);
    }
    public List<OutReceiver> saveAll(List<OutReceiver> outReceivers) {
        return repository.saveAll(outReceivers);
    }
}
