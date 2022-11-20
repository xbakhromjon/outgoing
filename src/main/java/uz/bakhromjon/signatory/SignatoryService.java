package uz.bakhromjon.signatory;

import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.feign.obj.WorkPlaceShortInfo;
import uz.bakhromjon.signatory.dto.SignatoryPDFDTO;
import uz.bakhromjon.utils.BaseUtils;

import java.util.UUID;

@Service
public class SignatoryService extends AbstractService<SignatoryRepository, SignatoryValidator, SignatoryMapper> {

    private final WorkPlaceFeignService workPlaceFeignService;
    private final ContentFileService contentFileService;
    private final BaseUtils baseUtils;

    public SignatoryService(SignatoryRepository repository, SignatoryValidator validator, SignatoryMapper mapper, WorkPlaceFeignService workPlaceFeignService, ContentFileService contentFileService, BaseUtils baseUtils) {
        super(repository, validator, mapper);
        this.workPlaceFeignService = workPlaceFeignService;
        this.contentFileService = contentFileService;
        this.baseUtils = baseUtils;
    }

    public Signatory save(Signatory signatory) {
        return repository.save(signatory);
    }

    public void edit(Signatory signatory, Long newWorkPlaceID) {
        if (!newWorkPlaceID.equals(signatory.getWorkPlaceID())) {
            Long signatoryUserID = workPlaceFeignService.getUserID(newWorkPlaceID);
            signatory.setWorkPlaceID(newWorkPlaceID);
            signatory.setUserID(signatoryUserID);
            repository.save(signatory);
        }
    }

    public boolean existsByID(UUID rejectedBy) {
        return repository.existsById(rejectedBy);
    }

    public void notReady(UUID id) {

    }

    public void reject(UUID rejectedByUUID) {
        repository.setStatus(rejectedByUUID, SignatoryStatus.REJECTED.getCode());
    }

    public void setStatus(UUID ID, Integer code) {
        repository.setStatus(ID, code);
    }

    public SignatoryPDFDTO makePDFDTO(Signatory signatory) {
        WorkPlaceShortInfo workPlaceInfoRemote = workPlaceFeignService.getWorkPlaceInfoRemote(signatory.getWorkPlaceID());
        String data = "" + signatory.getWorkPlaceID();
        String path = contentFileService.generateQRCode(data, 200, 200);
        String fullPosition = workPlaceInfoRemote.getDepartmentName() + " " + workPlaceInfoRemote.getPositionName();
        String shortName = baseUtils.getShortName(workPlaceInfoRemote.getFirstName(), workPlaceInfoRemote.getLastName());
        return new SignatoryPDFDTO(fullPosition, path, shortName);
    }
}
