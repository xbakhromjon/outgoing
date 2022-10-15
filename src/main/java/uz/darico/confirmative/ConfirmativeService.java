package uz.darico.confirmative;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.dto.ConfirmativePDFDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmativeService extends AbstractService<ConfirmativeRepository, ConfirmativeValidator, ConfirmativeMapper> {
    public ConfirmativeService(ConfirmativeRepository repository, ConfirmativeValidator validator, ConfirmativeMapper mapper) {
        super(repository, validator, mapper);
    }

    public List<Confirmative> refresh(List<Long> confirmativeWorkPlaceIDs, List<Confirmative> trashConfirmatives) {
        List<Confirmative> newConfirmatives = create(confirmativeWorkPlaceIDs);
        deleteAll(trashConfirmatives);
        return newConfirmatives;
    }


    public List<Confirmative> create(List<Long> confirmativeWorkPlaceIDs) {
        List<Confirmative> confirmatives = mapper.toEntity(confirmativeWorkPlaceIDs);
        return repository.saveAll(confirmatives);
    }

    public void deleteAll(List<Confirmative> confirmatives) {
        List<UUID> IDs = confirmatives.stream().map(AbstractEntity::getId).toList();
        repository.deleteFromRelatedTable(IDs);
        repository.deleteAll(IDs);
    }

    public List<Confirmative> saveAll(List<Confirmative> confirmatives) {
        return repository.saveAll(confirmatives);
    }

    public void notReadyByMissiveID(UUID missiveID) {
        repository.notReadyByMissiveID(missiveID);
    }

    public List<Confirmative> getAll(UUID missiveID) {
        return repository.getAll(missiveID);
    }

    public List<Confirmative> getAllSiblings(UUID confID) {
        return repository.getAllSiblings(confID);
    }

    public boolean nextPrevReady(UUID confID) {
        List<Confirmative> allSiblings = getAllSiblings(confID);
        Confirmative current = allSiblings.stream().filter(item -> item.getId().equals(confID)).findFirst().get();
        Integer orderNumber = current.getOrderNumber();
        Optional<Confirmative> nextOptional = allSiblings.stream().filter(item -> item.getOrderNumber() == orderNumber + 1).findFirst();
        if (nextOptional.isPresent()) {
            Confirmative next = nextOptional.get();
            repository.prevReady(next.getId());
            return true;
        }
        return false;
    }

    public void reject(UUID rejectedByUUID) {
        repository.setStatus(rejectedByUUID, ConfStatus.REJECTED.getCode());
    }

    public void setStatus(UUID ID, Integer code) {
        repository.setStatus(ID, code);
    }

    public List<ConfirmativePDFDTO> makePDFDTO(List<Confirmative> confirmatives) {
        return null;
    }
}
