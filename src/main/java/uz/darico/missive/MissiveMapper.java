package uz.darico.missive;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.exception.exception.UniversalException;
import uz.darico.feign.DepartmentFeignService;
import uz.darico.feign.OrganizationFeignService;
import uz.darico.feign.UserFeignService;
import uz.darico.feign.obj.UserInfo;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.InReceiverMapper;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missive.dto.*;
import uz.darico.missive.projections.MissiveListProjection;
import uz.darico.missive.projections.MissiveVersionShortInfoProjection;
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
import uz.darico.utils.OrgShortInfo;

import java.io.IOException;
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
    private final UserFeignService userFeignService;
    private final BaseUtils baseUtils;
    private final OrganizationFeignService organizationFeignService;

    public MissiveMapper(SenderMapper senderMapper, SignatoryMapper signatoryMapper, ConfirmativeMapper confirmativeMapper, OutReceiverMapper outReceiverMapper, InReceiverMapper inReceiverMapper, ContentFileService contentFileService, MissiveFileMapper missiveFileMapper, DepartmentFeignService departmentFeignService, UserFeignService userFeignService, BaseUtils baseUtils, OrganizationFeignService organizationFeignService) {
        this.senderMapper = senderMapper;
        this.signatoryMapper = signatoryMapper;
        this.confirmativeMapper = confirmativeMapper;
        this.outReceiverMapper = outReceiverMapper;
        this.inReceiverMapper = inReceiverMapper;
        this.contentFileService = contentFileService;
        this.missiveFileMapper = missiveFileMapper;
        this.departmentFeignService = departmentFeignService;
        this.userFeignService = userFeignService;
        this.baseUtils = baseUtils;
        this.organizationFeignService = organizationFeignService;
    }

    public Missive toEntity(MissiveCreateDTO createDTO) throws IOException {
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
        MissiveFile missiveFile = missiveFileMapper.toEntity(createDTO.getContent());
        return new Missive(createDTO.getOrgID(), sender, signatory, confirmatives, createDTO.getDepartmentID(), outReceivers, inReceivers, baseFiles, missiveFile);
    }

    public MissiveGetDTO toGetDTO(Missive missive) {
        MissiveFile missiveFiles = missive.getMissiveFile();
        OrgShortInfo orgShortInfo = organizationFeignService.getShortInfo(missive.getOrgID());
        return new MissiveGetDTO(missive.getId(), orgShortInfo.getName(), orgShortInfo.getEmail(), senderMapper.toGetDTO(missive.getSender()), signatoryMapper.toGetDTO(missive.getSignatory()), confirmativeMapper.toGetDTO(missive.getConfirmatives()),
                departmentFeignService.getName(missive.getDepartmentID()), outReceiverMapper.toGetDTO(missive.getOutReceivers()),
                inReceiverMapper.toGetDTO(missive.getInReceivers()), missive.getBaseFiles(), missiveFileMapper.toGetDTO(missive.getMissiveFile()),
                missive.getCreatedAt().toLocalDate());
    }

    public List<MissiveListDTO> toListDTO(List<MissiveListProjection> missiveListProjections) {
        List<MissiveListDTO> missiveListDTOs = new ArrayList<>();
        for (MissiveListProjection missiveListProjection : missiveListProjections) {
            UserInfo userInfo = userFeignService.getUserInfo(missiveListProjection.getSenderUserID());
            String departmentName = departmentFeignService.getName(missiveListProjection.getDepartmentID());
            MissiveListDTO missiveListDTO = new MissiveListDTOBuilder().setID(baseUtils.convertBytesToUUID(missiveListProjection.getID())).setDepartmentName(departmentName).setSenderFirstName(userInfo.getFirstName()).setSenderLastName(userInfo.getLastName()).setShortInfo(missiveListProjection.getShortInfo()).setOrgID(missiveListProjection.getOrgID()).setTotalCount(missiveListProjection.getTotalCount()).create();
            missiveListDTOs.add(missiveListDTO);
        }
        return missiveListDTOs;
    }

    public MissiveRawDTO toRawDTO(Missive missive) {
        List<Confirmative> confirmatives = missive.getConfirmatives();
        if (confirmatives == null) {
            confirmatives = new ArrayList<>();
        }
        List<OutReceiver> outReceivers = missive.getOutReceivers();
        if (outReceivers == null) {
            outReceivers = new ArrayList<>();
        }
        List<InReceiver> inReceivers = missive.getInReceivers();
        if (inReceivers == null) {
            inReceivers = new ArrayList<>();
        }
        MissiveFile missiveFile = missive.getMissiveFile();
        if (missiveFile == null) {
            throw new UniversalException("%s missiveID Missive files is null".formatted(missive.getId()), HttpStatus.BAD_REQUEST);
        }
        return new MissiveRawDTO(missive.getId(), missive.getOrgID(), missive.getSender().getWorkPlaceID(), missive.getSignatory().getWorkPlaceID(),
                confirmatives.stream().map(Confirmative::getWorkPlaceID).toList(), outReceivers.stream().map(OutReceiver::getCorrespondentID).toList(),
                inReceivers.stream().map(InReceiver::getCorrespondentID).toList(), missive.getBaseFiles(), missiveFile.getId(), missiveFile.getContent());
    }

    public List<MissiveVersionShortInfoDTO> toMissiveShortInfoDTO(List<MissiveVersionShortInfoProjection> missiveVersionShortInfoProjections) {
        List<MissiveVersionShortInfoDTO> missiveVersionShortInfoDTOs = new ArrayList<>();
        for (MissiveVersionShortInfoProjection missiveVersionShortInfoProjection : missiveVersionShortInfoProjections) {
            MissiveVersionShortInfoDTO missiveVersionShortInfoDTO = new MissiveVersionShortInfoDTO(baseUtils.convertBytesToUUID(missiveVersionShortInfoProjection.getID()), missiveVersionShortInfoProjection.getVersion());
            missiveVersionShortInfoDTOs.add(missiveVersionShortInfoDTO);
        }
        return missiveVersionShortInfoDTOs;
    }
}
