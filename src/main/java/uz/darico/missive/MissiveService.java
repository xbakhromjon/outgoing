package uz.darico.missive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeService;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.exception.exception.UniversalException;
import uz.darico.feedback.conf.ConfFeedBackService;
import uz.darico.feedback.conf.ConfFeedback;
import uz.darico.feedback.signatory.SignatoryFeedBackService;
import uz.darico.feedback.signatory.SignatoryFeedback;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.InReceiverService;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missive.dto.MissiveCreateDTO;
import uz.darico.missive.dto.MissiveGetDTO;
import uz.darico.missive.dto.MissiveRejectDTO;
import uz.darico.missive.dto.MissiveUpdateDTO;
import uz.darico.missiveFile.MissiveFile;
import uz.darico.missiveFile.MissiveFileService;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.OutReceiverService;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;
import uz.darico.sender.Sender;
import uz.darico.sender.SenderService;
import uz.darico.signatory.Signatory;
import uz.darico.signatory.SignatoryService;
import uz.darico.utils.BaseUtils;

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
    private final BaseUtils baseUtils;
    private final SignatoryFeedBackService signatoryFeedBackService;
    private final ConfFeedBackService confFeedBackService;


    public MissiveService(MissiveRepository repository, MissiveValidator validator, MissiveMapper mapper, ConfirmativeService confirmativeService, OutReceiverService outReceiverService, InReceiverService inReceiverService,
                          MissiveFileService missiveFileService, SignatoryService signatoryService,
                          ContentFileService contentFileService, SenderService senderService, BaseUtils baseUtils,
                          SignatoryFeedBackService signatoryFeedBackService, ConfFeedBackService confFeedBackService) {
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


    public ResponseEntity<?> delete(String id) {
        UUID ID = baseUtils.strToUUID(id);
        repository.delete(ID);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> get(String id) {
        UUID ID = baseUtils.strToUUID(id);
        Missive missive = getMissive(ID);
        MissiveGetDTO missiveGetDTO = mapper.toGetDTO(missive);
        return ResponseEntity.ok(missiveGetDTO);
    }


    public ResponseEntity<?> readyForSender(String id) {
        UUID ID = baseUtils.strToUUID(id);
        repository.readyForSender(ID);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> readyForConfirmative(String confId) {
        UUID confID = baseUtils.strToUUID(confId);
        repository.readyForConf(confID);
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
            SignatoryFeedback signatoryFeedback =
                    signatoryFeedBackService.create(rejectDTO);
            List<SignatoryFeedback> signatoryFeedbacks = sender.getSignatoryFeedbacks();
            signatoryFeedbacks.add(signatoryFeedback);
            sender.setSignatoryFeedbacks(signatoryFeedbacks);
        } else {
            ConfFeedback confFeedback = confFeedBackService.create(rejectDTO);
            List<ConfFeedback> confFeedbacks = sender.getConfFeedbacks();
            confFeedbacks.add(confFeedback);
            sender.setConfFeedbacks(confFeedbacks);
        }
        senderService.save(sender);
        confirmativeService.notReady(ID);
        signatoryService.notReady(ID);
        senderService.notReady(ID);
        return ResponseEntity.ok(true);
    }

}
