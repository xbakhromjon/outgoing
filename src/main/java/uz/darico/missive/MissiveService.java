package uz.darico.missive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.confirmative.ConfirmativeService;
import uz.darico.confirmative.dto.ConfirmativeShortInfoDTO;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.exception.exception.UniversalException;
import uz.darico.feedback.conf.ConfFeedBackService;
import uz.darico.feedback.signatory.SignatoryFeedBackService;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.InReceiverService;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missive.dto.*;
import uz.darico.missive.projections.MissiveListProjection;
import uz.darico.missiveFile.MissiveFile;
import uz.darico.missiveFile.MissiveFileService;
import uz.darico.missiveFile.dto.MissiveFileCreateDTO;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.OutReceiverService;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;
import uz.darico.sender.Sender;
import uz.darico.sender.SenderService;
import uz.darico.signatory.Signatory;
import uz.darico.signatory.SignatoryService;
import uz.darico.utils.BaseUtils;
import uz.darico.utils.SearchDTO;
import uz.darico.utils.Tab;

import java.util.List;
import java.util.Objects;
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
    private final BaseUtils baseUtils;
    private final SignatoryFeedBackService signatoryFeedBackService;
    private final ConfFeedBackService confFeedBackService;
    private final ConfirmativeMapper confirmativeMapper;

    public MissiveService(MissiveRepository repository, MissiveValidator validator, MissiveMapper mapper, ConfirmativeService confirmativeService, OutReceiverService outReceiverService, InReceiverService inReceiverService,
                          MissiveFileService missiveFileService, SignatoryService signatoryService,
                          ContentFileService contentFileService, SenderService senderService, BaseUtils baseUtils,
                          SignatoryFeedBackService signatoryFeedBackService, ConfFeedBackService confFeedBackService,
                          ConfirmativeMapper confirmativeMapper) {
        super(repository, validator, mapper);
        this.confirmativeService = confirmativeService;
        this.outReceiverService = outReceiverService;
        this.inReceiverService = inReceiverService;
        this.missiveFileService = missiveFileService;
        this.signatoryService = signatoryService;
        this.contentFileService = contentFileService;
        this.senderService = senderService;
        this.baseUtils = baseUtils;
        this.signatoryFeedBackService = signatoryFeedBackService;
        this.confFeedBackService = confFeedBackService;
        this.confirmativeMapper = confirmativeMapper;
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
        Missive missive = getPersist(updateDTO.getID());

        if (!missive.getSender().getWorkPlaceID().equals(updateDTO.getWorkPlaceID())) {
            throw new UniversalException("Sender not updatable", HttpStatus.BAD_REQUEST);
        }

        if (missive.getSender().getIsReadyToSend()) {
            throw new UniversalException("Missive not updatable", HttpStatus.BAD_REQUEST);
        }

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

        List<MissiveFile> newMissiveFiles = missiveFileService.refresh(updateDTO.getContent(), missive.getMissiveFiles());
        missive.setMissiveFiles(newMissiveFiles);

        repository.save(missive);
        return ResponseEntity.ok(true);
    }

    public Missive getPersist(UUID ID) {
        Optional<Missive> missiveOptional = repository.find(ID);
        if (missiveOptional.isEmpty()) {
            throw new UniversalException("Missive not found", HttpStatus.BAD_REQUEST);
        }
        return missiveOptional.get();
    }


    public ResponseEntity<?> delete(String id) {
        UUID ID = baseUtils.strToUUID(id);
        repository.delete(ID);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> get(String id) {
        UUID ID = baseUtils.strToUUID(id);
        Missive missive = getPersist(ID);
        MissiveGetDTO missiveGetDTO = mapper.toGetDTO(missive);
        return ResponseEntity.ok(missiveGetDTO);
    }


    public ResponseEntity<?> readyForSender(String id) {
        UUID ID = baseUtils.strToUUID(id);
        repository.readyForSender(ID);
        return ResponseEntity.ok(true);
    }


    public ResponseEntity<?> readyForConf(String confId) {
        UUID confID = baseUtils.strToUUID(confId);
        repository.readyForConf(confID);
        boolean canPrevReady = confirmativeService.nextPrevReady(confID);
        if (!canPrevReady) {
            repository.readyByConfID(confID);
        }
        return ResponseEntity.ok(true);
    }


    public ResponseEntity<?> sign(String id) {
        UUID ID = baseUtils.strToUUID(id);
        repository.sign(ID);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> reject(MissiveRejectDTO rejectDTO) {
        UUID ID = baseUtils.strToUUID(rejectDTO.getMissive());
        UUID rejectedByUUID = baseUtils.strToUUID(rejectDTO.getRejectedBy());
        rejectDTO.setRejectedByUUID(rejectedByUUID);
        Sender sender = senderService.getPersistByMissiveID(ID);
        if (signatoryService.existsByID(rejectedByUUID)) {
            signatoryFeedBackService.add(rejectDTO, sender);
        } else {
            confFeedBackService.add(rejectDTO, sender);
        }
        sender.setIsReadyToSend(false);
        senderService.save(sender);
        confirmativeService.notReadyByMissiveID(ID);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> getList(SearchDTO searchDTO) {
        List<MissiveListProjection> projections = getSketchies(searchDTO);
        List<MissiveListDTO> listDTOs = complete(projections);
        return ResponseEntity.ok(listDTOs);
    }

    public List<MissiveListDTO> complete(List<MissiveListProjection> missiveListProjections) {
        List<MissiveListDTO> missiveListDTOs = mapper.toListDTO(missiveListProjections);
        for (MissiveListDTO missiveListDTO : missiveListDTOs) {
            UUID ID = missiveListDTO.getID();
            // confirmative
            List<Confirmative> confirmatives = confirmativeService.getAll(ID);
            List<ConfirmativeShortInfoDTO> confirmativeShortInfoDTOs = confirmativeMapper.toShortInfoDTO(confirmatives);
            missiveListDTO.setConfirmatives(confirmativeShortInfoDTOs);
            // file
            List<ContentFile> baseFiles = contentFileService.getAll(ID);
            missiveListDTO.setBaseFiles(baseFiles);
            // missiveFile
            List<MissiveFile> missiveFiles = missiveFileService.getAll(ID);
            missiveListDTO.setMissiveFiles(missiveFiles);
        }
        return missiveListDTOs;
    }

    public List<MissiveListProjection> getSketchies(SearchDTO searchDTO) {
        int offset = searchDTO.getPage() * searchDTO.getSize();
        searchDTO.setOffset(offset);
        if (Objects.equals(searchDTO.getTab(), Tab.HOMAKI.getCode())) {
            return repository.getSketchies(searchDTO.getWorkPlace(),
                    searchDTO.getSize(), offset);
        }
        return getInProcess(searchDTO);
    }

    public List<MissiveListProjection> getInProcess(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.JARAYONDA.getCode())) {
            return repository.getInProcesses(searchDTO.getWorkPlace(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }

        return getForConfirm(searchDTO);
    }

    public List<MissiveListProjection> getForConfirm(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.TASDIQLASH_UCHUN.getCode())) {
            return repository.getForConfirm(searchDTO.getWorkPlace(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }

        return getConfirmed(searchDTO);
    }

    public List<MissiveListProjection> getConfirmed(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.TASDIQLANGAN.getCode())) {
            return repository.getConfirmed(searchDTO.getWorkPlace(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }

        return getForSign(searchDTO);
    }

    public List<MissiveListProjection> getForSign(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.IMZOLASH_UCHUN.getCode())) {
            return repository.getForSign(searchDTO.getWorkPlace(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }

        return getSigned(searchDTO);
    }

    public List<MissiveListProjection> getSigned(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.IMZOLANGAN.getCode())) {
            return repository.getSigned(searchDTO.getWorkPlace(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }

        return getSent(searchDTO);
    }

    public List<MissiveListProjection> getSent(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.YUBORILGAN.getCode())) {
            return repository.getSent(searchDTO.getWorkPlace(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }
        throw new UniversalException("%s tab code incorrect".formatted(searchDTO.getTab()), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> createNewVersion(MissiveFileCreateDTO createDTO) {
        Missive missive = getPersist(createDTO.getMissiveID());
        validator.validForNewVersion(createDTO, missive);
        MissiveFile newVersion = missiveFileService.createNewVersion(createDTO);
        List<MissiveFile> missiveFiles = missive.getMissiveFiles();
        missiveFiles.add(newVersion);
        repository.save(missive);
        return ResponseEntity.ok(newVersion.getId());
    }

    public ResponseEntity<?> deleteVersion(UUID missiveFileID) {
        missiveFileService.delete(missiveFileID);
        return ResponseEntity.ok(true);
    }

    
}

