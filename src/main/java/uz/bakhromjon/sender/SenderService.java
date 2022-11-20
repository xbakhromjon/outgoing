package uz.bakhromjon.sender;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.feign.obj.WorkPlaceShortInfo;
import uz.bakhromjon.sender.dto.SenderPDFDTO;
import uz.bakhromjon.utils.BaseUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class SenderService extends AbstractService<SenderRepository, SenderValidator, SenderMapper> {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final ContentFileService contentFileService;
    private final BaseUtils baseUtils;

    public SenderService(SenderRepository repository, SenderValidator validator, SenderMapper mapper,
                         WorkPlaceFeignService workPlaceFeignService, ContentFileService contentFileService,
                         BaseUtils baseUtils) {
        super(repository, validator, mapper);
        this.workPlaceFeignService = workPlaceFeignService;
        this.contentFileService = contentFileService;
        this.baseUtils = baseUtils;
    }

    public Sender save(Sender sender) {
        return repository.save(sender);
    }

    public Sender getPersistByMissiveID(UUID missiveID) {
        Optional<Sender> optional = repository.findByMissiveId(missiveID);
        return optional.orElseThrow(() -> {
            throw new UniversalException("Sender Not Found", HttpStatus.BAD_REQUEST);
        });
    }

    public void notReady(UUID id) {

    }

    public SenderPDFDTO makePDFDTO(Sender sender) {
        WorkPlaceShortInfo workPlaceInfoRemote = workPlaceFeignService.getWorkPlaceInfoRemote(sender.getWorkPlaceID());
        String data = "" + sender.getWorkPlaceID();
        String path = contentFileService.generateQRCode(data, 200, 200);
        String fullPosition = workPlaceInfoRemote.getDepartmentName() + " " + workPlaceInfoRemote.getPositionName();
        String shortName = baseUtils.getShortName(workPlaceInfoRemote.getFirstName(), workPlaceInfoRemote.getLastName());
        return new SenderPDFDTO(fullPosition, path, shortName);
    }
}
