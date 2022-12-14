package uz.bakhromjon.confirmative;

import org.springframework.stereotype.Service;
import uz.bakhromjon.base.entity.AbstractEntity;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.confirmative.dto.ConfirmativePDFDTO;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.feign.obj.WorkPlaceShortInfo;
import uz.bakhromjon.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConfirmativeService extends AbstractService<ConfirmativeRepository, ConfirmativeValidator, ConfirmativeMapper> {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final ContentFileService contentFileService;
    private final BaseUtils baseUtils;

    public ConfirmativeService(ConfirmativeRepository repository, ConfirmativeValidator validator, ConfirmativeMapper mapper, WorkPlaceFeignService workPlaceFeignService, ContentFileService contentFileService, BaseUtils baseUtils) {
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
        List<UUID> IDs = confirmatives.stream().map(AbstractEntity::getId).collect(Collectors.toList());
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

    public boolean isReadyToSign(UUID confID) {
        List<Confirmative> allSiblings = getAllSiblings(confID);
//        Confirmative current = allSiblings.stream().filter(item -> item.getId().equals(confID)).findFirst().get();
//        Integer orderNumber = current.getOrderNumber();
//        Optional<Confirmative> nextOptional = allSiblings.stream().filter(item -> item.getOrderNumber() == orderNumber + 1).findFirst();
//        if (nextOptional.isPresent()) {
//            Confirmative next = nextOptional.get();
//            repository.prevReady(next.getId());
//            return true;
//        }

        if (allSiblings.stream().allMatch(Confirmative::getIsReadyToSend)) return true;
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
