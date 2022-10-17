package uz.darico.confirmative;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.dto.ConfirmativePDFDTO;
import uz.darico.contentFile.ContentFileService;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.feign.obj.WorkPlaceShortInfo;
import uz.darico.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmativeService extends AbstractService<ConfirmativeRepository, ConfirmativeValidator, ConfirmativeMapper> {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final ContentFileService contentFileService;
    private final BaseUtils baseUtils;

    public ConfirmativeService(ConfirmativeRepository repository, ConfirmativeValidator validator, ConfirmativeMapper mapper,
                               WorkPlaceFeignService workPlaceFeignService, ContentFileService contentFileService,
                               BaseUtils baseUtils) {
        super(repository, validator, mapper);
        this.workPlaceFeignService = workPlaceFeignService;
        this.contentFileService = contentFileService;
        this.baseUtils = baseUtils;
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
        List<ConfirmativePDFDTO> confirmativePDFDTOs = new ArrayList<>();
        for (Confirmative confirmative : confirmatives) {
            WorkPlaceShortInfo workPlaceInfoRemote = workPlaceFeignService.getWorkPlaceInfoRemote(confirmative.getWorkPlaceID());
            String data = "" + confirmative.getWorkPlaceID();
            String path = contentFileService.generateQRCode(data, 200, 200);
            String fullPosition = workPlaceInfoRemote.getDepartmentName() + " " + workPlaceInfoRemote.getPositionName();
            String shortName = baseUtils.getShortName(workPlaceInfoRemote.getFirstName(), workPlaceInfoRemote.getLastName());
            ConfirmativePDFDTO confirmativePDFDTO = new ConfirmativePDFDTO(fullPosition, path, shortName);
            confirmativePDFDTOs.add(confirmativePDFDTO);
        }
        return confirmativePDFDTOs;
    }
}
