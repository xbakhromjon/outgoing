package uz.bakhromjon.missive;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.confirmative.Confirmative;
import uz.bakhromjon.confirmative.ConfirmativeMapper;
import uz.bakhromjon.contentFile.ContentFile;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.department.DepartmentService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.feign.OrganizationFeignService;
import uz.bakhromjon.feign.obj.UserInfo;
import uz.bakhromjon.fishka.FishkaMapper;
import uz.bakhromjon.fishka.FishkaService;
import uz.bakhromjon.inReceiver.InReceiver;
import uz.bakhromjon.inReceiver.InReceiverMapper;
import uz.bakhromjon.inReceiver.dto.InReceiverCreateDTO;
import uz.bakhromjon.missive.dto.*;
import uz.bakhromjon.missive.projections.MissiveListProjection;
import uz.bakhromjon.missive.projections.MissiveVersionShortInfoProjection;
import uz.bakhromjon.missiveFile.MissiveFile;
import uz.bakhromjon.missiveFile.MissiveFileMapper;
import uz.bakhromjon.outReceiver.OutReceiver;
import uz.bakhromjon.outReceiver.OutReceiverMapper;
import uz.bakhromjon.outReceiver.dto.OutReceiverCreateDTO;
import uz.bakhromjon.sender.Sender;
import uz.bakhromjon.sender.SenderMapper;
import uz.bakhromjon.signatory.Signatory;
import uz.bakhromjon.signatory.SignatoryMapper;
import uz.bakhromjon.user.UserService;
import uz.bakhromjon.utils.BaseUtils;
import uz.bakhromjon.utils.OrgShortInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class MissiveMapper implements BaseMapper {
    private final SenderMapper senderMapper;
    private final SignatoryMapper signatoryMapper;
    private final ConfirmativeMapper confirmativeMapper;
    private final OutReceiverMapper outReceiverMapper;
    private final InReceiverMapper inReceiverMapper;
    private final ContentFileService contentFileService;
    private final MissiveFileMapper missiveFileMapper;
    private final DepartmentService departmentService;
    private final UserService userService;
    private final BaseUtils baseUtils;
    private final OrganizationFeignService organizationFeignService;
    private final FishkaService fishkaService;
    private final FishkaMapper fishkaMapper;

    public MissiveMapper(SenderMapper senderMapper, SignatoryMapper signatoryMapper, ConfirmativeMapper confirmativeMapper,
                         OutReceiverMapper outReceiverMapper, InReceiverMapper inReceiverMapper,
                         ContentFileService contentFileService, MissiveFileMapper missiveFileMapper,
                         DepartmentService departmentService, UserService userService, BaseUtils baseUtils, OrganizationFeignService organizationFeignService, FishkaService fishkaService,
                         FishkaMapper fishkaMapper) {
        this.senderMapper = senderMapper;
        this.signatoryMapper = signatoryMapper;
        this.confirmativeMapper = confirmativeMapper;
        this.outReceiverMapper = outReceiverMapper;
        this.inReceiverMapper = inReceiverMapper;
        this.contentFileService = contentFileService;
        this.missiveFileMapper = missiveFileMapper;
        this.departmentService = departmentService;
        this.userService = userService;
        this.baseUtils = baseUtils;
        this.organizationFeignService = organizationFeignService;
        this.fishkaService = fishkaService;
        this.fishkaMapper = fishkaMapper;
    }

    public Missive toEntity(MissiveCreateDTO createDTO) throws IOException {
        UUID workPlaceID = createDTO.getWorkPlaceID();
        Sender sender = senderMapper.toEntity(workPlaceID);
        UUID signatoryWorkPlaceID = createDTO.getSignatoryWorkPlaceID();
        Signatory signatory = signatoryMapper.toEntity(signatoryWorkPlaceID);
        List<UUID> confirmativeWorkPlaceIDs = createDTO.getConfirmativeWorkPlaceIDs();
        List<Confirmative> confirmatives = confirmativeMapper.toEntity(confirmativeWorkPlaceIDs);
        List<OutReceiverCreateDTO> outReceiverCreateDTOs = createDTO.getOutReceivers();
        List<OutReceiver> outReceivers = outReceiverMapper.toEntity(outReceiverCreateDTOs);
        List<InReceiverCreateDTO> inReceiverCreateDTOs = createDTO.getInReceivers();
        List<InReceiver> inReceivers = inReceiverMapper.toEntity(inReceiverCreateDTOs);
        List<ContentFile> baseFiles = contentFileService.getContentFiles(createDTO.getBaseFileIDs());
        MissiveFile missiveFile = missiveFileMapper.toEntity(createDTO.getContent());
        return new Missive(createDTO.getOrgID(), sender, signatory, confirmatives, createDTO.getDepartmentID(), outReceivers, inReceivers, baseFiles, missiveFile, createDTO.getFishkaID());
    }

    public MissiveGetDTO toGetDTO(Missive missive) {
        MissiveFile missiveFiles = missive.getMissiveFile();
        OrgShortInfo orgShortInfo = organizationFeignService.getShortInfo(missive.getOrgID());
        return new MissiveGetDTO(missive.getId(), missive.getRootVersionID(), orgShortInfo.getName(), orgShortInfo.getEmail(), senderMapper.toGetDTO(missive.getSender()), signatoryMapper.toGetDTO(missive.getSignatory()), confirmativeMapper.toGetDTO(missive.getConfirmatives()),
                departmentService.getName(missive.getDepartmentID()), outReceiverMapper.toGetDTO(missive.getOutReceivers()),
                inReceiverMapper.toGetDTO(missive.getInReceivers()), missive.getBaseFiles(), missiveFileMapper.toGetDTO(missive.getMissiveFile()),
                missive.getCreatedAt().toLocalDate(), missive.getReadyPDF());
    }

    public List<MissiveListDTO> toListDTO(List<MissiveListProjection> missiveListProjections) {
        List<MissiveListDTO> missiveListDTOs = new ArrayList<>();
        for (MissiveListProjection missiveListProjection : missiveListProjections) {
            UserInfo userInfo = userService.getUserInfo(missiveListProjection.getSenderUserID());
            String departmentName = departmentService.getName(missiveListProjection.getDepartmentID());
            MissiveListDTO missiveListDTO = new MissiveListDTOBuilder().
                    setID(baseUtils.convertBytesToUUID(missiveListProjection.getID())).
                    setDepartmentName(departmentName).setSenderFirstName(userInfo.getFirstName()).
                    setSenderLastName(userInfo.getLastName()).
                    setShortInfo(missiveListProjection.getShortInfo()).
                    setOrgID(missiveListProjection.getOrgID()).setTotalCount(missiveListProjection.getTotalCount())
                    .setNumber(missiveListProjection.getNumber())
                    .setRegisteredAt(missiveListProjection.getRegisteredAt())
                    .create();
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
            throw new UniversalException(String.format("%s missiveID Missive files is null", missive.getId()), HttpStatus.BAD_REQUEST);
        }
        List<OutReceiverCreateDTO> outReceiverCreateDTOs = outReceiverMapper.toCreateDTO(outReceivers);
        List<InReceiverCreateDTO> inReceiverCreateDTOs = inReceiverMapper.toCreateDTO(inReceivers);
        return new MissiveRawDTO(missive.getId(), missive.getRootVersionID(), missive.getOrgID(), missive.getSender().getWorkPlaceID(), missive.getSignatory().getWorkPlaceID(),
                confirmatives.stream().map(Confirmative::getWorkPlaceID).collect(Collectors.toList()), outReceiverCreateDTOs,
                inReceiverCreateDTOs, missive.getBaseFiles(), missiveFile.getId(), missiveFile.getContent(), fishkaService.getFishkaGetDTO(missive.getFishkaID()));
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
