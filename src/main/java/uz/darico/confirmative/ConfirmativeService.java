package uz.darico.confirmative;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;

import java.util.List;
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
}
