package uz.darico.missive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.ConfStatus;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.confirmative.ConfirmativeService;
import uz.darico.confirmative.dto.ConfirmativePDFDTO;
import uz.darico.confirmative.dto.ConfirmativeShortInfoDTO;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.exception.exception.UniversalException;
import uz.darico.feedback.conf.ConfFeedBackService;
import uz.darico.feedback.feedback.FeedBackService;
import uz.darico.feedback.feedback.dto.FeedbackGetDTO;
import uz.darico.feedback.signatory.SignatoryFeedBackService;
import uz.darico.feign.OrganizationFeignService;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.fishka.FishkaService;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.InReceiverService;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missive.dto.*;
import uz.darico.missive.projections.MissiveListProjection;
import uz.darico.missive.projections.MissiveVersionShortInfoProjection;
import uz.darico.missiveFile.MissiveFile;
import uz.darico.missiveFile.MissiveFileService;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.OutReceiverService;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;
import uz.darico.sender.Sender;
import uz.darico.sender.SenderService;
import uz.darico.sender.dto.SenderPDFDTO;
import uz.darico.signatory.Signatory;
import uz.darico.signatory.SignatoryService;
import uz.darico.signatory.SignatoryStatus;
import uz.darico.signatory.dto.SignatoryPDFDTO;
import uz.darico.utils.BaseUtils;
import uz.darico.utils.ResponsePage;
import uz.darico.utils.SearchDTO;
import uz.darico.utils.Tab;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MissiveService extends AbstractService<MissiveRepository, MissiveValidator, MissiveMapper> {
    private final ConfirmativeService confirmativeService;
    private final OutReceiverService outReceiverService;
    private final InReceiverService inReceiverService;
    private final MissiveFileService missiveFileService;

    private final SignatoryService signatoryService;
    private final ContentFileService contentFileService;
    private final SenderService senderService;
    private final BaseUtils baseUtils;
    private final ConfFeedBackService confFeedBackService;
    private final FeedBackService feedBackService;
    private final ConfirmativeMapper confirmativeMapper;
    private final OrganizationFeignService organizationFeignService;
    private final FishkaService fishkaService;

    private final WorkPlaceFeignService workPlaceFeignService;

    public MissiveService(MissiveRepository repository, MissiveValidator validator, MissiveMapper mapper, ConfirmativeService confirmativeService, OutReceiverService outReceiverService, InReceiverService inReceiverService, MissiveFileService missiveFileService, SignatoryService signatoryService, ContentFileService contentFileService, SenderService senderService, BaseUtils baseUtils, SignatoryFeedBackService signatoryFeedBackService, ConfFeedBackService confFeedBackService, ConfirmativeMapper confirmativeMapper, OrganizationFeignService organizationFeignService, FishkaService fishkaService, WorkPlaceFeignService workPlaceFeignService, FeedBackService feedBackService) {
        super(repository, validator, mapper);
        this.confirmativeService = confirmativeService;
        this.outReceiverService = outReceiverService;
        this.inReceiverService = inReceiverService;
        this.missiveFileService = missiveFileService;
        this.signatoryService = signatoryService;
        this.contentFileService = contentFileService;
        this.senderService = senderService;
        this.baseUtils = baseUtils;
        this.confFeedBackService = confFeedBackService;
        this.confirmativeMapper = confirmativeMapper;
        this.organizationFeignService = organizationFeignService;
        this.fishkaService = fishkaService;
        this.workPlaceFeignService = workPlaceFeignService;
        this.feedBackService = feedBackService;
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

        missive.setShortInfo(updateDTO.getShortInfo());
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
        FeedbackGetDTO feedbackGetDTO = feedBackService.getFeedbackDTO(missive.getRootVersionID(), missive.getSender().getWorkPlaceID(), workPlaceID);
        missiveGetDTO.setFeedback(feedbackGetDTO);
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
        else if (missive.getConfirmatives().stream().map(Confirmative::getWorkPlaceID).collect(Collectors.toList()).contains(workPlaceID)) {
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
        boolean readyToSign = confirmativeService.isReadyToSign(confID);
        if (readyToSign) {
            repository.readyByConfID(confID);
        }
        return ResponseEntity.ok(true);
    }


    public ResponseEntity<?> sign(String id) {
        UUID ID = baseUtils.strToUUID(id);
        repository.sign(ID);
        // generate PDF
        PDFDTO pdfdto = makePDFDTO(ID);
        String path = generatePDF(pdfdto);
        Missive missive = getPersist(ID);
        ContentFile contentFile = contentFileService.create(path);
        missive.setReadyPDF(contentFile);
        missive = repository.save(missive);
//        outReceiverService.send(missive);
//        inReceiverService.send(missive);
        return ResponseEntity.ok(true);
    }

    private PDFDTO makePDFDTO(UUID ID) {
        Missive missive = getPersist(ID);
        String fishkaPath = fishkaService.getPersist(missive.getFishkaID()).getFile().getPath();
        LocalDateTime signedAt = missive.getSignatory().getSignedAt();
        SignatoryPDFDTO signatoryPDFDTO = signatoryService.makePDFDTO(missive.getSignatory());
        SenderPDFDTO senderPDFDTO = senderService.makePDFDTO(missive.getSender());
        List<ConfirmativePDFDTO> confirmativePDFDTOs = confirmativeService.makePDFDTO(missive.getConfirmatives());
        return new PDFDTO(fishkaPath, baseUtils.getDateWord(signedAt.toLocalDate()), missive.getNumber(), missive.getMissiveFile().getContent(), signatoryPDFDTO, senderPDFDTO, confirmativePDFDTOs);
    }

    public String generatePDF(PDFDTO pdfdto) {
        String content = String.format("<div style=\"font-size: 12px; line-height: 15px\">%s</div>", pdfdto.getContent());
        String fishka = String.format("   <img src=%s", pdfdto.getFishkaPath()) + " width=\"100%\" height=\"100%\"> <br> <br>";
        String dateNumber = String.format("<strong style=\"color: blue\">%s</strong>", pdfdto.getDate()) + String.format("<a> <strong>№ %s</strong> </a><br> <br>", pdfdto.getNumber());
        SignatoryPDFDTO signatoryPDFDTO = pdfdto.getSignatoryPDFDTO();
        String signatory = "    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" + "        <tr>\n" + MessageFormat.format("<td style=\"max-width: 200px; padding-left: 20px\"> <span>" + " <p style=\"font-size: 14px;\">{0}:</p> </span> </td> ", signatoryPDFDTO.getFullPosition()) + "<td> <img  style=\"\" src=\"" + signatoryPDFDTO.getQrCodePath() + "\" height=\"120px\" width=\"120px\" > </td>" + "<td> " + MessageFormat.format("<span style=\"flex: 1\">  <p>{0}</p> </span>  </div>", signatoryPDFDTO.getShortName()) + "</td>" + "        </tr>\n" + "    </table>\n";
        StringBuilder html = new StringBuilder(fishka + dateNumber + content + signatory);
        SenderPDFDTO senderPDFDTO = pdfdto.getSenderPDFDTO();
        String sender = "    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" + "        <tr>\n" + MessageFormat.format("<td style=\"max-width: 200px; padding-left: 20px\"> <span>" + " <p style=\"font-size: 14px;\">{0}:</p> </span> </td> ", senderPDFDTO.getFullPosition()) + "<td> <img  style=\"\" src=\"" + senderPDFDTO.getQrCodePath() + "\" height=\"120px\" width=\"120px\" > </td>" + "<td> " + MessageFormat.format("<span style=\"flex: 1\">  <p>{0}</p> </span>  </div>", senderPDFDTO.getShortName()) + "</td>" + "        </tr>\n" + "    </table>\n";
        html.append(sender);

        List<ConfirmativePDFDTO> confirmativePDFDTOs = pdfdto.getConfirmativePDFDTOs();
        for (int i = 0, confirmativePDFDTOsSize = confirmativePDFDTOs.size(); i < confirmativePDFDTOsSize; i++) {

            ConfirmativePDFDTO confirmativePDFDTO = confirmativePDFDTOs.get(i);
            StringBuilder confirmative = new StringBuilder("");
            if (i == 0) {
                confirmative.append("    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" + "        <tr>\n" + MessageFormat.format("<td style=\"max-width: 200px; padding-left: 20px\"> <span>" + "<p style=\"font-size: 14px;\"><strong> Kelishildi: </strong>  <br> {0}:</p> </span> </td> ", confirmativePDFDTO.getFullPosition()) + "<td> <img  style=\"\" src=\"" + confirmativePDFDTO.getQrCodePath() + "\" height=\"120px\" width=\"120px\" > </td>" + "<td> " + MessageFormat.format("<span style=\"flex: 1\">  <p>{0}</p> </span>  </div>", confirmativePDFDTO.getShortName()) + "</td>" + "        </tr>\n" + "    </table>\n");
            } else {
                confirmative.append("    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" + "        <tr>\n" + MessageFormat.format("<td style=\"max-width: 200px; padding-left: 20px\"> <span>" + " <p style=\"font-size: 14px;\">{0}:</p> </span> </td> ", confirmativePDFDTO.getFullPosition()) + "<td> <img  style=\"\" src=\"" + confirmativePDFDTO.getQrCodePath() + "\" height=\"120px\" width=\"120px\" > </td>" + "<td> " + MessageFormat.format("<span style=\"flex: 1\">  <p>{0}</p> </span>  </div>", confirmativePDFDTO.getShortName()) + "</td>" + "        </tr>\n" + "    </table>\n");
            }
            html.append(confirmative);
        }

        return contentFileService.writeAsPDF(html.toString());
    }

    public ResponseEntity<?> reject(MissiveRejectDTO rejectDTO) {
        UUID ID = baseUtils.strToUUID(rejectDTO.getRootMissiveID());
        Sender sender = senderService.getPersistByMissiveID(ID);
        feedBackService.add(rejectDTO, sender.getWorkPlaceID());
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

            // correspondent
            List<OutReceiver> outReceivers = outReceiverService.getAllByMissiveID(ID);
            List<InReceiver> inReceivers = inReceiverService.getAllByMissiveID(ID);

            List<String> correspondents = new ArrayList<>(outReceivers.stream().map(item -> organizationFeignService.getShortInfo(item.getCorrespondentID()).getName()).collect(Collectors.toList()));
            correspondents.addAll(inReceivers.stream().map(item -> organizationFeignService.getShortInfo(item.getCorrespondentID()).getName()).collect(Collectors.toList()));
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
            return repository.getSketchies(searchDTO.getWorkPlace(), searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(), searchDTO.getSize(), searchDTO.getOffset());
        }
        return getInProcess(searchDTO);
    }

    public List<MissiveListProjection> getInProcess(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.JARAYONDA.getCode())) {
            return repository.getInProcesses(searchDTO.getWorkPlace(), searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(), searchDTO.getSize(), searchDTO.getOffset());
        }

        return getForConfirm(searchDTO);
    }

    public List<MissiveListProjection> getForConfirm(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.TASDIQLASH_UCHUN.getCode())) {
            return repository.getForConfirm(searchDTO.getWorkPlace(), searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(), searchDTO.getSize(), searchDTO.getOffset());
        }

        return getConfirmed(searchDTO);
    }

    public List<MissiveListProjection> getConfirmed(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.TASDIQLANGAN.getCode())) {
            return repository.getConfirmed(searchDTO.getWorkPlace(), searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(), searchDTO.getSize(), searchDTO.getOffset());
        }

        return getForSign(searchDTO);
    }

    public List<MissiveListProjection> getForSign(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.IMZOLASH_UCHUN.getCode())) {
            return repository.getForSign(searchDTO.getWorkPlace(), searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(), searchDTO.getSize(), searchDTO.getOffset());
        }

        return getSigned(searchDTO);
    }

    public List<MissiveListProjection> getSigned(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.IMZOLANGAN.getCode())) {
            if (workPlaceFeignService.isOfficeManager(searchDTO.getWorkPlace())) {
                return repository.getSignedForOfficeManager(searchDTO.getOrgID(), searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(), searchDTO.getSize(), searchDTO.getOffset());
            }
            return repository.getSigned(searchDTO.getWorkPlace(), searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(), searchDTO.getSize(), searchDTO.getOffset());
        }

        return getSent(searchDTO);
    }

    public List<MissiveListProjection> getSent(SearchDTO searchDTO) {
        if (Objects.equals(searchDTO.getTab(), Tab.YUBORILGAN.getCode())) {
            return repository.getSent(searchDTO.getWorkPlace(), searchDTO.getConfirmativeWorkPlace(), searchDTO.getShortInfo(), searchDTO.getCorrespondent(), searchDTO.getSize(), searchDTO.getOffset());
        }
        throw new UniversalException(String.format("%s tab code incorrect", searchDTO.getTab()), HttpStatus.BAD_REQUEST);
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
        FeedbackGetDTO feedbackGetDTO = feedBackService.getFeedbackDTO(missive.getRootVersionID(), workPlaceID, ID);
        missiveGetDTO.setFeedback(feedbackGetDTO);
        return ResponseEntity.ok(missiveGetDTO);
    }

    public ResponseEntity<?> getCount(Long workPlaceID, Long orgID) {
        List<CountDTO> countDTOs = new ArrayList<>();
        Integer sketchyCount = repository.getSketchyCount(workPlaceID);
        countDTOs.add(new CountDTO(Tab.HOMAKI.getCode(), sketchyCount));
        Integer inProcessCount = repository.getInProcessCount(workPlaceID);
        countDTOs.add(new CountDTO(Tab.JARAYONDA.getCode(), inProcessCount));
        Integer forConfirmCount = repository.getForConfirmCount(workPlaceID);
        countDTOs.add(new CountDTO(Tab.TASDIQLASH_UCHUN.getCode(), forConfirmCount));
        Integer confirmedCount = repository.getConfirmedCount(workPlaceID);
        countDTOs.add(new CountDTO(Tab.TASDIQLANGAN.getCode(), confirmedCount));
        Integer forSignCount = repository.getForSignCount(workPlaceID);
        countDTOs.add(new CountDTO(Tab.IMZOLASH_UCHUN.getCode(), forSignCount));
        Integer signedCount = null;
        if (workPlaceFeignService.isOfficeManager(workPlaceID)) {
            signedCount = repository.getSignedForOfficeManagerCount(orgID);
        } else {
            signedCount = repository.getSignedCount(workPlaceID);
        }
        countDTOs.add(new CountDTO(Tab.IMZOLANGAN.getCode(), signedCount));
        Integer sentCount = repository.getSentCount(workPlaceID);
        countDTOs.add(new CountDTO(Tab.YUBORILGAN.getCode(), sentCount));
        return ResponseEntity.ok(countDTOs);
    }

    public ResponseEntity<?> updateContent(ContentUpdateDTO contentUpdateDTO) {
        repository.updateContent(contentUpdateDTO.getMissiveID(), contentUpdateDTO.getContent());
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> register(MissiveRegisteDTO missiveRegisteDTO) {
        validator.validForRegister(missiveRegisteDTO);
        Missive missive = getPersist(missiveRegisteDTO.getId());
        missive.setJournalID(missiveRegisteDTO.getJournalID());
        missive.setRegisteredAt(missiveRegisteDTO.getRegisteredAt());
        missive.setNumber(missiveRegisteDTO.getNumber());
        missive.setIsConfirmOfficeManager(true);
        repository.save(missive);
        return ResponseEntity.ok(true);
    }


}

