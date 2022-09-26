package uz.darico.missive;

import org.springframework.stereotype.Component;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.InReceiverMapper;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missive.dto.MissiveCreateDTO;
import uz.darico.missive.dto.MissiveGetDTO;
import uz.darico.missive.dto.MissiveListDTO;
import uz.darico.missive.projections.MissiveListProjection;
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
    private final SenderMapper senderMapper;
    private final SignatoryMapper signatoryMapper;
    private final ConfirmativeMapper confirmativeMapper;
    private final OutReceiverMapper outReceiverMapper;
    private final InReceiverMapper inReceiverMapper;
    private final ContentFileService contentFileService;
    private final MissiveFileMapper missiveFileMapper;

    public MissiveMapper(SenderMapper senderMapper, SignatoryMapper signatoryMapper, ConfirmativeMapper confirmativeMapper, OutReceiverMapper outReceiverMapper, InReceiverMapper inReceiverMapper, ContentFileService contentFileService, MissiveFileMapper missiveFileMapper) {
        this.senderMapper = senderMapper;
        this.signatoryMapper = signatoryMapper;
        this.confirmativeMapper = confirmativeMapper;
        this.outReceiverMapper = outReceiverMapper;
        this.inReceiverMapper = inReceiverMapper;
        this.contentFileService = contentFileService;
        this.missiveFileMapper = missiveFileMapper;
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
        ContentFile missiveFileContent = contentFileService.getContentFile(createDTO.getMissiveFileContentID());
        MissiveFile missiveFile = missiveFileMapper.toEntity(missiveFileContent);
        return new Missive(createDTO.getOrgID(), sender, signatory, confirmatives, createDTO.getDepartmentID(),
                outReceivers, inReceivers, baseFiles, Collections.singletonList(missiveFile));
    }

    public MissiveGetDTO toGetDTO(Missive missive) {
        return new MissiveGetDTO();
    }

    public List<MissiveListDTO> toListDTO(List<MissiveListProjection> missiveListProjections) {

        return null;
    }
}
