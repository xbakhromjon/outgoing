package uz.darico.missive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.confirmative.ConfirmativeRepository;
import uz.darico.confirmative.ConfirmativeService;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileRepository;
import uz.darico.contentFile.ContentFileService;
import uz.darico.exception.exception.UniversalException;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.InReceiverMapper;
import uz.darico.inReceiver.InReceiverRepository;
import uz.darico.inReceiver.InReceiverService;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missive.dto.MissiveCreateDTO;
import uz.darico.missive.dto.MissiveUpdateDTO;
import uz.darico.missiveFile.MissiveFile;
import uz.darico.missiveFile.MissiveFileMapper;
import uz.darico.missiveFile.MissiveFileRepository;
import uz.darico.missiveFile.MissiveFileService;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.OutReceiverMapper;
import uz.darico.outReceiver.OutReceiverRepository;
import uz.darico.outReceiver.OutReceiverService;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;
import uz.darico.sender.SenderMapper;
import uz.darico.sender.SenderRepository;
import uz.darico.sender.SenderService;
import uz.darico.signatory.Signatory;
import uz.darico.signatory.SignatoryMapper;
import uz.darico.signatory.SignatoryRepository;
import uz.darico.signatory.SignatoryService;
import uz.darico.utils.EntityGetter;
import uz.darico.workPlace.WorkPlaceFeignService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MissiveService extends AbstractService<MissiveRepository, MissiveValidator, MissiveMapper> {
    private final ConfirmativeService confirmativeService;
    private final OutReceiverService outReceiverService;
    private final InReceiverService inReceiverService;
    private final MissiveFileService missiveFileService;

    private final SignatoryService signatoryService;
    private final ContentFileService contentFileService;
    private final SenderService senderService;


    public MissiveService(MissiveRepository repository, MissiveValidator validator, MissiveMapper mapper, ConfirmativeService confirmativeService, OutReceiverService outReceiverService, InReceiverService inReceiverService, MissiveFileService missiveFileService, SignatoryService signatoryService, ContentFileService contentFileService, SenderService senderService) {
        super(repository, validator, mapper);
        this.confirmativeService = confirmativeService;
        this.outReceiverService = outReceiverService;
        this.inReceiverService = inReceiverService;
        this.missiveFileService = missiveFileService;
        this.signatoryService = signatoryService;
        this.contentFileService = contentFileService;
        this.senderService = senderService;
    }

    public ResponseEntity<?> create(MissiveCreateDTO createDTO) {
        validator.validForCreate(createDTO);
        Missive missive = mapper.toEntity(createDTO);
        missive.setSender(senderService.save(missive.getSender()));
        missive.setSignatory(signatoryService.save(missive.getSignatory()));
        missive.setConfirmatives(confirmativeService.saveAll(missive.getConfirmatives()));
        missive.setOutReceivers(outReceiverService.saveAll(missive.getOutReceivers()));
        missive.setInReceivers(inReceiverService.saveAll(missive.getInReceivers()));
        missive.setBaseFiles(contentFileService.saveAll(missive.getBaseFiles()));
        missive.setMissiveFiles(missiveFileService.saveAll(missive.getMissiveFiles()));
        Missive saved = repository.save(missive);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> update(MissiveUpdateDTO updateDTO) {
        validator.validForUpdate(updateDTO);
        Missive missive = getMissive(updateDTO.getID());

        Signatory signatory = missive.getSignatory();
        signatoryService.edit(signatory, updateDTO.getSignatoryWorkPlaceID());

        List<Long> newConfirmativeWorkPlaceIDs = updateDTO.getConfirmativeWorkPlaceIDs();
        List<Confirmative> newConfirmatives = confirmativeService.refresh(newConfirmativeWorkPlaceIDs, missive.getConfirmatives());
        missive.setConfirmatives(newConfirmatives);

        List<OutReceiverCreateDTO> outReceiverCreateDTOs = updateDTO.getOutReceivers();
        List<OutReceiver> newOutReceivers = outReceiverService.refresh(outReceiverCreateDTOs, missive.getOutReceivers());
        missive.setOutReceivers(newOutReceivers);

        List<InReceiverCreateDTO> inReceiverCreateDTOs = updateDTO.getInReceivers();
        List<InReceiver> newInReceivers = inReceiverService.refresh(inReceiverCreateDTOs, missive.getInReceivers());
        missive.setInReceivers(newInReceivers);

        List<UUID> baseFileIDs = updateDTO.getBaseFileIDs();
        List<ContentFile> newContentFiles = contentFileService.refresh(baseFileIDs, missive.getBaseFiles());
        missive.setBaseFiles(newContentFiles);

        ContentFile missiveFileContent = contentFileService.getContentFile(updateDTO.getMissiveFileContentID());
        List<MissiveFile> newMissiveFiles = missiveFileService.refresh(missiveFileContent, missive.getMissiveFiles());
        missive.setMissiveFiles(newMissiveFiles);

        repository.save(missive);
        return ResponseEntity.ok(true);
    }

    public Missive getMissive(UUID ID) {
        Optional<Missive> missiveOptional = repository.findById(ID);
        if (missiveOptional.isEmpty()) {
            throw new UniversalException("Missive not found", HttpStatus.BAD_REQUEST);
        }
        return missiveOptional.get();
    }


}
