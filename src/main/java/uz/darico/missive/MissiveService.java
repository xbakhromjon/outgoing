package uz.darico.missive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.ConfStatus;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.confirmative.ConfirmativeService;
import uz.darico.confirmative.dto.ConfirmativeShortInfoDTO;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.exception.exception.UniversalException;
import uz.darico.feedback.conf.ConfFeedBackService;
import uz.darico.feedback.signatory.SignatoryFeedBackService;
import uz.darico.feign.OrganizationFeignService;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.InReceiverService;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missive.dto.*;
import uz.darico.missive.projections.MissiveListProjection;
import uz.darico.missive.projections.MissiveVersionShortInfoProjection;
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
import uz.darico.signatory.SignatoryStatus;
import uz.darico.utils.BaseUtils;
import uz.darico.utils.ResponsePage;
import uz.darico.utils.SearchDTO;
import uz.darico.utils.Tab;

import java.io.IOException;
import java.util.*;

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
    private final OrganizationFeignService organizationFeignService;

    public MissiveService(MissiveRepository repository, MissiveValidator validator, MissiveMapper mapper, ConfirmativeService confirmativeService, OutReceiverService outReceiverService, InReceiverService inReceiverService,
                          MissiveFileService missiveFileService, SignatoryService signatoryService,
                          ContentFileService contentFileService, SenderService senderService, BaseUtils baseUtils,
                          SignatoryFeedBackService signatoryFeedBackService, ConfFeedBackService confFeedBackService,
                          ConfirmativeMapper confirmativeMapper, OrganizationFeignService organizationFeignService) {
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
        this.organizationFeignService = organizationFeignService;
    }

    public ResponseEntity<?> create(MissiveCreateDTO createDTO) throws IOException {
        validator.validForCreate(createDTO);
        Missive missive = mapper.toEntity(createDTO);
        missive.setSender(senderService.save(missive.getSender()));
        missive.setSignatory(signatoryService.save(missive.getSignatory()));
        missive.setConfirmatives(confirmativeService.saveAll(missive.getConfirmatives()));
        missive.setOutReceivers(outReceiverService.saveAll(missive.getOutReceivers()));
        missive.setInReceivers(inReceiverService.saveAll(missive.getInReceivers()));
        missive.setBaseFiles(contentFileService.saveAll(missive.getBaseFiles()));
        missive.setMissiveFile(missiveFileService.save(missive.getMissiveFile()));
        missive.setShortInfo(createDTO.getShortInfo());
        Missive saved = repository.save(missive);
        repository.setRootVersionID(saved.getId(), saved.getId());
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> update(MissiveUpdateDTO updateDTO) throws IOException {
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

        List<MissiveFile> newMissiveFiles = missiveFileService.refresh(updateDTO.getMissiveFileID(), updateDTO.getContent());
//        missive.setMissiveFiles(newMissiveFiles);

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

    public ResponseEntity<?> get(Long workPlaceID, String id) {
        UUID ID = baseUtils.strToUUID(id);
        Missive missive = getPersist(ID);
        MissiveGetDTO missiveGetDTO = mapper.toGetDTO(missive);
        setStatus(workPlaceID, ID);
        return ResponseEntity.ok(missiveGetDTO);
    }

    private void setStatus(Long workPlaceID, UUID missiveID) {
        // signatory
        Missive missive = getPersist(missiveID);
        if (missive.getSignatory().getWorkPlaceID().equals(workPlaceID)) {
            if (missive.getSignatory().getStatusCode().equals(SignatoryStatus.NOT_VIEWED.getCode())) {
                signatoryService.setStatus(missive.getSignatory().getId(), SignatoryStatus.VIEWED.getCode());
            }
        }
        // confirmative
        else if (missive.getConfirmatives().stream().map(Confirmative::getWorkPlaceID).toList().contains(workPlaceID)) {
            for (Confirmative confirmative : missive.getConfirmatives()) {
                if (confirmative.getWorkPlaceID().equals(workPlaceID)) {
                    if (confirmative.getStatusCode().equals(ConfStatus.NOT_VIEWED.getCode())) {
                        confirmativeService.setStatus(confirmative.getId(), ConfStatus.VIEWED.getCode());
                    }
                }
            }
        }
        // sender
        else if (missive.getSender().getWorkPlaceID().equals(workPlaceID)) {

        }
        // other
        else {

        }

    }

    public ResponseEntity<?> getRaw(UUID ID) {
        Missive missive = getPersist(ID);
        MissiveRawDTO missiveRawDTO = mapper.toRawDTO(missive);
        return ResponseEntity.ok(missiveRawDTO);
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
        MissiveFile lastVersion = missiveFileService.getLastVersion(ID);
        outReceiverService.send(ID, lastVersion);
        inReceiverService.send(ID, lastVersion);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> reject(MissiveRejectDTO rejectDTO) {
        UUID ID = baseUtils.strToUUID(rejectDTO.getMissive());
        UUID rejectedByUUID = baseUtils.strToUUID(rejectDTO.getRejectedBy());
        rejectDTO.setRejectedByUUID(rejectedByUUID);
        Sender sender = senderService.getPersistByMissiveID(ID);
        if (signatoryService.existsByID(rejectedByUUID)) {
            signatoryFeedBackService.add(rejectDTO, sender);
            signatoryService.reject(rejectedByUUID);
        } else {
            confFeedBackService.add(rejectDTO, sender);
            confirmativeService.reject(rejectedByUUID);
        }
        sender.setIsReadyToSend(false);
        senderService.save(sender);
        confirmativeService.notReadyByMissiveID(ID);
        repository.notReady(ID);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> getList(SearchDTO searchDTO) {
        List<MissiveListProjection> projections = getSketchies(searchDTO);
        List<MissiveListDTO> listDTOs = complete(projections);
        if (listDTOs.isEmpty() || listDTOs.get(0) == null) {
            return ResponseEntity.ok(ResponsePage.getEmptyInstance());
        }
        ResponsePage<MissiveListDTO> responsePage = baseUtils.toResponsePage(listDTOs, searchDTO.getPage(), searchDTO.getSize(), listDTOs.get(0).getTotalCount());
        return ResponseEntity.ok(responsePage);
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

            // correspondent
            List<OutReceiver> outReceivers = outReceiverService.getAllByMissiveID(ID);
            List<InReceiver> inReceivers = inReceiverService.getAllByMissiveID(ID);

            List<String> correspondents = new ArrayList<>(outReceivers.stream().map(item -> organizationFeignService.getShortInfo(item.getCorrespondentID()).getName()).toList());
            correspondents.addAll(inReceivers.stream().map(item -> organizationFeignService.getShortInfo(item.getCorrespondentID()).getName()).toList());
            missiveListDTO.setCorrespondent(correspondents);
        }

        return missiveListDTOs;
    }

    public List<MissiveListProjection> getSketchies(SearchDTO searchDTO) {
        int offset = searchDTO.getPage() * searchDTO.getSize();
        searchDTO.setOffset(offset);
        if (Objects.equals(searchDTO.getTab(), Tab.HOMAKI.getCode())) {
            String shortInfo = null;
            if (searchDTO.getShortInfo() != null) {
                shortInfo = "%" + searchDTO.getShortInfo() + "%";
            }
            searchDTO.setShortInfo(shortInfo);
            return repository.getSketchies(searchDTO.getWorkPlace(),
                    searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }
        return getInProcess(searchDTO);
    }

    public List<MissiveListProjection> getInProcess(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.JARAYONDA.getCode())) {
            return repository.getInProcesses(searchDTO.getWorkPlace(),
                    searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }

        return getForConfirm(searchDTO);
    }

    public List<MissiveListProjection> getForConfirm(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.TASDIQLASH_UCHUN.getCode())) {
            return repository.getForConfirm(searchDTO.getWorkPlace(),
                    searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }

        return getConfirmed(searchDTO);
    }

    public List<MissiveListProjection> getConfirmed(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.TASDIQLANGAN.getCode())) {
            return repository.getConfirmed(searchDTO.getWorkPlace(),
                    searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }

        return getForSign(searchDTO);
    }

    public List<MissiveListProjection> getForSign(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.IMZOLASH_UCHUN.getCode())) {
            return repository.getForSign(searchDTO.getWorkPlace(),
                    searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }

        return getSigned(searchDTO);
    }

    public List<MissiveListProjection> getSigned(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.IMZOLANGAN.getCode())) {
            return repository.getSigned(searchDTO.getWorkPlace(),
                    searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }

        return getSent(searchDTO);
    }

    public List<MissiveListProjection> getSent(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.YUBORILGAN.getCode())) {
            return repository.getSent(searchDTO.getWorkPlace(),
                    searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(),
                    searchDTO.getSize(), searchDTO.getOffset());
        }
        throw new UniversalException("%s tab code incorrect".formatted(searchDTO.getTab()), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> createNewVersion(MissiveCreateDTO createDTO) throws IOException {
        validator.validForNewVersion(createDTO);
//        Missive oldVersion = getPersist(createDTO.getRootID());
        Missive oldVersion = repository.getLastVersion(createDTO.getRootID());
        ResponseEntity<?> response = create(createDTO);
        UUID rootVersionID = oldVersion.getRootVersionID();
        oldVersion.setIsLastVersion(false);
        repository.save(oldVersion);
        Missive newVersion = (Missive) response.getBody();
        newVersion.setVersion(oldVersion.getVersion() + 1);
        newVersion.setRootVersionID(rootVersionID);
        repository.save(newVersion);
//        validator.validForNewVersion(createDTO, missive);
//        MissiveFile newVersion = missiveFileService.createNewVersion(createDTO);
//        List<MissiveFile> missiveFiles = missive.getMissiveFiles();
//        missiveFiles.add(newVersion);
//        repository.save(missive);

        return ResponseEntity.ok(newVersion.getId());
    }

    public ResponseEntity<?> deleteVersion(UUID missiveFileID) {
        missiveFileService.delete(missiveFileID);
        return ResponseEntity.ok(true);
    }


    public Integer getCountByJournal(UUID ID) {
        // TODO: 10/10/22 implements here
        return 0;
    }

    public ResponseEntity<?> getSketchy(Long workPlaceID, String id) {
        UUID ID = baseUtils.strToUUID(id);
        Missive missive = getPersist(ID);
        MissiveGetDTO missiveGetDTO = mapper.toGetDTO(missive);
        List<MissiveVersionShortInfoProjection> missiveVersionShortInfoProjections = repository.getAllVersions(ID);
        List<MissiveVersionShortInfoDTO> missiveVersionShortInfoDTOs = mapper.toMissiveShortInfoDTO(missiveVersionShortInfoProjections);
        missiveGetDTO.setVersions(missiveVersionShortInfoDTOs);
        setStatus(workPlaceID, ID);
        return ResponseEntity.ok(missiveGetDTO);
    }

}

