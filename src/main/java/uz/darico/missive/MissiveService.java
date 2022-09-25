package uz.darico.missive;

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
import uz.darico.signatory.Signatory;
import uz.darico.signatory.SignatoryMapper;
import uz.darico.signatory.SignatoryRepository;
import uz.darico.signatory.SignatoryService;
import uz.darico.utils.EntityGetter;
import uz.darico.workPlace.WorkPlaceFeignService;

import java.util.Collections;
import java.util.List;

@Service
public class MissiveService extends AbstractService<MissiveRepository, MissiveValidator, MissiveMapper> {

    private final SenderRepository senderRepository;
    private final SignatoryRepository signatoryRepository;
    private final ConfirmativeRepository confirmativeRepository;
    private final InReceiverRepository inReceiverRepository;
    private final OutReceiverRepository outReceiverRepository;
    private final ContentFileRepository contentFileRepository;
    private final MissiveFileRepository missiveFileRepository;
    private final EntityGetter entityGetter;
    private final WorkPlaceFeignService workPlaceFeignService;
    private final ConfirmativeService confirmativeService;
    private final OutReceiverService outReceiverService;
    private final InReceiverService inReceiverService;
    private final MissiveFileService missiveFileService;

    private final SignatoryService signatoryService;
    private final ContentFileService contentFileService;
    private final SenderMapper senderMapper;
    private final SignatoryMapper signatoryMapper;
    private final ConfirmativeMapper confirmativeMapper;
    private final OutReceiverMapper outReceiverMapper;
    private final InReceiverMapper inReceiverMapper;
    private final MissiveFileMapper missiveFileMapper;

    public MissiveService(MissiveRepository repository, MissiveValidator validator, MissiveMapper mapper, SenderRepository senderRepository, SignatoryRepository signatoryRepository, ConfirmativeRepository confirmativeRepository, InReceiverRepository inReceiverRepository, OutReceiverRepository outReceiverRepository, ContentFileRepository contentFileRepository, MissiveFileRepository missiveFileRepository, EntityGetter entityGetter, WorkPlaceFeignService workPlaceFeignService, ConfirmativeService confirmativeService, OutReceiverService outReceiverService, InReceiverService inReceiverService, MissiveFileService missiveFileService, ConfirmativeRepository confirmativeRepository1, SignatoryRepository signatoryRepository1, OutReceiverRepository outReceiverRepository1, InReceiverRepository inReceiverRepository1, ContentFileRepository contentFileRepository1, MissiveFileRepository missiveFileRepository1, SignatoryService signatoryService, ContentFileService contentFileService, SenderMapper senderMapper, SignatoryMapper signatoryMapper, ConfirmativeMapper confirmativeMapper, OutReceiverMapper outReceiverMapper, InReceiverMapper inReceiverMapper, MissiveFileMapper missiveFileMapper) {
        super(repository, validator, mapper);
        this.senderRepository = senderRepository;
        this.signatoryRepository = signatoryRepository;
        this.confirmativeRepository = confirmativeRepository;
        this.inReceiverRepository = inReceiverRepository;
        this.outReceiverRepository = outReceiverRepository;
        this.contentFileRepository = contentFileRepository;
        this.missiveFileRepository = missiveFileRepository;
        this.entityGetter = entityGetter;
        this.workPlaceFeignService = workPlaceFeignService;
        this.confirmativeService = confirmativeService;
        this.outReceiverService = outReceiverService;
        this.inReceiverService = inReceiverService;
        this.missiveFileService = missiveFileService;
        this.signatoryService = signatoryService;
        this.contentFileService = contentFileService;
        this.senderMapper = senderMapper;
        this.signatoryMapper = signatoryMapper;
        this.confirmativeMapper = confirmativeMapper;
        this.outReceiverMapper = outReceiverMapper;
        this.inReceiverMapper = inReceiverMapper;
        this.missiveFileMapper = missiveFileMapper;
    }

    public ResponseEntity<?> create(MissiveCreateDTO createDTO) {
        validator.validForCreate(createDTO);
        Missive missive = mapper.toEntity(createDTO);
        missive.setSender(senderRepository.save(missive.getSender()));
        missive.setSignatory(signatoryRepository.save(missive.getSignatory()));
        missive.setConfirmatives(confirmativeRepository.saveAll(missive.getConfirmatives()));
        missive.setOutReceivers(outReceiverRepository.saveAll(missive.getOutReceivers()));
        missive.setInReceivers(inReceiverRepository.saveAll(missive.getInReceivers()));
        missive.setBaseFiles(contentFileRepository.saveAll(missive.getBaseFiles()));
        missive.setMissiveFiles(missiveFileRepository.saveAll(missive.getMissiveFiles()));
        Missive saved = repository.save(missive);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> update(MissiveUpdateDTO updateDTO) {
        validator.validForUpdate(updateDTO);
        Missive missive = entityGetter.getMissive(updateDTO.getID());
        Long signatoryWorkPlaceID = updateDTO.getSignatoryWorkPlaceID();
        Signatory signatory = missive.getSignatory();
        if (!signatoryWorkPlaceID.equals(signatory.getWorkPlaceID())) {
            Long signatoryUserID = workPlaceFeignService.getUserID(updateDTO.getSignatoryWorkPlaceID());
            signatory.setWorkPlaceID(updateDTO.getSignatoryWorkPlaceID());
            signatory.setUserID(signatoryUserID);
            signatoryRepository.save(signatory);
        }
        List<Long> confirmativeWorkPlaceIDs = updateDTO.getConfirmativeWorkPlaceIDs();
        List<Confirmative> confirmatives = confirmativeMapper.toEntity(confirmativeWorkPlaceIDs);
        confirmativeService.deleteAll(missive.getConfirmatives());
        List<OutReceiverCreateDTO> outReceiverCreateDTOs = updateDTO.getOutReceivers();
        List<OutReceiver> outReceivers = outReceiverMapper.toEntity(outReceiverCreateDTOs);
        outReceiverService.deleteAll(missive.getOutReceivers());
        List<InReceiverCreateDTO> inReceiverCreateDTOs = updateDTO.getInReceivers();
        List<InReceiver> inReceivers = inReceiverMapper.toEntity(inReceiverCreateDTOs);
        inReceiverService.deleteAll(missive.getInReceivers());
        List<ContentFile> baseFiles = contentFileService.getContentFiles(updateDTO.getBaseFileIDs());
        contentFileService.deleteAll(missive.getBaseFiles());
        ContentFile missiveFileContent = contentFileService.getContentFile(updateDTO.getMissiveFileID());
        MissiveFile missiveFile = missiveFileMapper.toEntity(missiveFileContent);
        List<MissiveFile> savedMissiveFiles = missiveFileRepository.saveAll(Collections.singletonList(missiveFile));
        List<Confirmative> newConfirmatives = confirmativeRepository.saveAll(confirmatives);
        List<OutReceiver> newOutReceivers = outReceiverRepository.saveAll(outReceivers);
        List<InReceiver> newInReceivers = inReceiverRepository.saveAll(inReceivers);
        List<ContentFile> newContentFiles = contentFileRepository.saveAll(baseFiles);
        missive.setConfirmatives(newConfirmatives);
        missive.setOutReceivers(newOutReceivers);
        missive.setInReceivers(newInReceivers);
        missive.setBaseFiles(newContentFiles);
        missive.setMissiveFiles(savedMissiveFiles);
        repository.save(missive);
        return ResponseEntity.ok(true);
    }

}
