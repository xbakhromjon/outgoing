package uz.darico.missive;

import org.springframework.stereotype.Component;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.feign.DepartmentFeignService;
import uz.darico.feign.UserFeignService;
import uz.darico.feign.obj.UserInfo;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.InReceiverMapper;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missive.dto.MissiveCreateDTO;
import uz.darico.missive.dto.MissiveGetDTO;
import uz.darico.missive.dto.MissiveListDTO;
import uz.darico.missive.dto.MissiveListDTOBuilder;
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
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.utils.BaseUtils;

import java.util.*;


@Component
public class MissiveMapper implements BaseMapper {
    private final SenderMapper senderMapper;
    private final SignatoryMapper signatoryMapper;
    private final ConfirmativeMapper confirmativeMapper;
    private final OutReceiverMapper outReceiverMapper;
    private final InReceiverMapper inReceiverMapper;
    private final ContentFileService contentFileService;
    private final MissiveFileMapper missiveFileMapper;
    private final DepartmentFeignService departmentFeignService;
    private final WorkPlaceFeignService workPlaceFeignService;
    private final UserFeignService userFeignService;
    private final BaseUtils baseUtils;

    public MissiveMapper(SenderMapper senderMapper, SignatoryMapper signatoryMapper, ConfirmativeMapper confirmativeMapper, OutReceiverMapper outReceiverMapper, InReceiverMapper inReceiverMapper, ContentFileService contentFileService, MissiveFileMapper missiveFileMapper,
                         DepartmentFeignService departmentFeignService, WorkPlaceFeignService workPlaceFeignService, UserFeignService userFeignService,
                         BaseUtils baseUtils) {
        this.senderMapper = senderMapper;
        this.signatoryMapper = signatoryMapper;
        this.confirmativeMapper = confirmativeMapper;
        this.outReceiverMapper = outReceiverMapper;
        this.inReceiverMapper = inReceiverMapper;
        this.contentFileService = contentFileService;
        this.missiveFileMapper = missiveFileMapper;
        this.departmentFeignService = departmentFeignService;
        this.workPlaceFeignService = workPlaceFeignService;
        this.userFeignService = userFeignService;
        this.baseUtils = baseUtils;
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
        List<MissiveFile> missiveFiles = missive.getMissiveFiles();
        missiveFiles
                .sort(Comparator.comparing(MissiveFile::getVersion));
        return new MissiveGetDTO(missive.getOrgID(), missive.getSender(), missive
                .getSignatory(), missive.getConfirmatives(), missive.getDepartmentID(), missive.getOutReceivers(),
                missive.getInReceivers(), missive.getBaseFiles(), missiveFiles, missive.getIsReady());
    }

    public List<MissiveListDTO> toListDTO(List<MissiveListProjection> missiveListProjections) {
        List<MissiveListDTO> missiveListDTOs = new ArrayList<>();
        for (MissiveListProjection missiveListProjection : missiveListProjections) {
            UserInfo userInfo = userFeignService.getUserInfo(missiveListProjection.getSenderUserID());
            MissiveListDTO missiveListDTO = new MissiveListDTOBuilder().setID(baseUtils.convertBytesToUUID(missiveListProjection.getID())).setDepartmentName(departmentFeignService.getName(missiveListProjection.getDepartmentID())).
                    setSenderFirstName(userInfo.getFirstName()).setSenderLastName(userInfo.getLastName()).setShortInfo(missiveListProjection.getShortInfo()).setOrgID(missiveListProjection.getOrgID()).create();
            missiveListDTOs.add(missiveListDTO);
        }
        return missiveListDTOs;
    }
}
