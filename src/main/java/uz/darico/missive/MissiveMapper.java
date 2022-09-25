package uz.darico.missive;

import org.springframework.stereotype.Component;
import uz.darico.confirmative.ConfirmativeRepository;
import uz.darico.confirmative.ConfirmativeService;
import uz.darico.contentFile.ContentFileRepository;
import uz.darico.inReceiver.InReceiverRepository;
import uz.darico.inReceiver.InReceiverService;
import uz.darico.missive.dto.MissiveUpdateDTO;
import uz.darico.missiveFile.MissiveFileRepository;
import uz.darico.missiveFile.MissiveFileService;
import uz.darico.outReceiver.OutReceiverRepository;
import uz.darico.outReceiver.OutReceiverService;
import uz.darico.signatory.SignatoryRepository;
import uz.darico.signatory.SignatoryService;
import uz.darico.utils.EntityGetter;
import uz.darico.workPlace.WorkPlaceFeignService;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.InReceiverMapper;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missive.dto.MissiveCreateDTO;
import uz.darico.missiveFile.MissiveFile;
import uz.darico.missiveFile.MissiveFileMapper;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.OutReceiverMapper;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;
import uz.darico.sender.Sender;
import uz.darico.sender.SenderMapper;
import uz.darico.signatory.Signatory;
import uz.darico.signatory.SignatoryMapper;

import java.util.Collections;
import java.util.List;


@Component
public class MissiveMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final SenderMapper senderMapper;
    private final SignatoryMapper signatoryMapper;
    private final ConfirmativeMapper confirmativeMapper;
    private final OutReceiverMapper outReceiverMapper;
    private final InReceiverMapper inReceiverMapper;
    private final ContentFileService contentFileService;
    private final MissiveFileMapper missiveFileMapper;
    private final MissiveRepository missiveRepository;
    private final EntityGetter entityGetter;
    private final ConfirmativeService confirmativeService;
    private final OutReceiverService outReceiverService;
    private final InReceiverService inReceiverService;
    private final MissiveFileService missiveFileService;
    private final ConfirmativeRepository confirmativeRepository;
    private final SignatoryRepository signatoryRepository;
    private final OutReceiverRepository outReceiverRepository;
    private final InReceiverRepository inReceiverRepository;
    private final ContentFileRepository contentFileRepository;
    private final MissiveFileRepository missiveFileRepository;
    private final SignatoryService signatoryService;

    public MissiveMapper(WorkPlaceFeignService workPlaceFeignService, SenderMapper senderMapper,
                         SignatoryMapper signatoryMapper, ConfirmativeMapper confirmativeMapper,
                         OutReceiverMapper outReceiverMapper, InReceiverMapper inReceiverMapper,
                         ContentFileService contentFileService, MissiveFileMapper missiveFileMapper,
                         MissiveRepository missiveRepository, EntityGetter entityGetter,
                         ConfirmativeService confirmativeService, OutReceiverService outReceiverService,
                         InReceiverService inReceiverService, MissiveFileService missiveFileService,
                         ConfirmativeRepository confirmativeRepository, SignatoryRepository signatoryRepository,
                         OutReceiverRepository outReceiverRepository, InReceiverRepository inReceiverRepository,
                         ContentFileRepository contentFileRepository, MissiveFileRepository missiveFileRepository,
                         SignatoryService signatoryService) {
        this.workPlaceFeignService = workPlaceFeignService;
        this.senderMapper = senderMapper;
        this.signatoryMapper = signatoryMapper;
        this.confirmativeMapper = confirmativeMapper;
        this.outReceiverMapper = outReceiverMapper;
        this.inReceiverMapper = inReceiverMapper;
        this.contentFileService = contentFileService;
        this.missiveFileMapper = missiveFileMapper;
        this.missiveRepository = missiveRepository;
        this.entityGetter = entityGetter;
        this.confirmativeService = confirmativeService;
        this.outReceiverService = outReceiverService;
        this.inReceiverService = inReceiverService;
        this.missiveFileService = missiveFileService;
        this.confirmativeRepository = confirmativeRepository;
        this.signatoryRepository = signatoryRepository;
        this.outReceiverRepository = outReceiverRepository;
        this.inReceiverRepository = inReceiverRepository;
        this.contentFileRepository = contentFileRepository;
        this.missiveFileRepository = missiveFileRepository;
        this.signatoryService = signatoryService;
    }

    public Missive toEntity(MissiveCreateDTO createDTO) {
        Long workPlaceID = createDTO.getWorkPlaceID();
        Sender sender = senderMapper.toEntity(workPlaceID);
        Long signatoryWorkPlaceID = createDTO.getSignatoryWorkPlaceID();
        Signatory signatory = signatoryMapper.toEntity(signatoryWorkPlaceID);
        List<Long> confirmativeWorkPlaceIDs = createDTO.getConfirmativeWorkPlaceIDs();
        List<Confirmative> confirmatives = confirmativeMapper.toEntity(confirmativeWorkPlaceIDs);
        List<OutReceiverCreateDTO> outReceiverCreateDTOs = createDTO.getOutReceivers();
        List<OutReceiver> outReceivers = outReceiverMapper.toEntity(outReceiverCreateDTOs);
        List<InReceiverCreateDTO> inReceiverCreateDTOs = createDTO.getInReceivers();
        List<InReceiver> inReceivers = inReceiverMapper.toEntity(inReceiverCreateDTOs);
        List<ContentFile> baseFiles = contentFileService.getContentFiles(createDTO.getBaseFileIDs());
        ContentFile missiveFileContent = contentFileService.getContentFile(createDTO.getMissiveFileID());
        MissiveFile missiveFile = missiveFileMapper.toEntity(missiveFileContent);
        Missive missive = new Missive(createDTO.getOrgID(), sender, signatory, confirmatives, createDTO.getDepartmentID(),
                outReceivers, inReceivers, baseFiles, Collections.singletonList(missiveFile));
        return missive;
    }

    public Missive toEntity(MissiveUpdateDTO updateDTO) {
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
        missiveRepository.save(missive);
        return missive;
    }
}
