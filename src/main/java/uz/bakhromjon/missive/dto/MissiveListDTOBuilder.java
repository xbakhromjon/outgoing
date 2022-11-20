package uz.bakhromjon.missive.dto;

import uz.bakhromjon.confirmative.dto.ConfirmativeShortInfoDTO;
import uz.bakhromjon.contentFile.ContentFile;
import uz.bakhromjon.missiveFile.MissiveFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class MissiveListDTOBuilder {
    private UUID ID;
    private Long orgID;
    private String departmentName;
    private String senderFirstName;
    private String senderLastName;
    private List<ConfirmativeShortInfoDTO> confirmatives;
    private String shortInfo;
    private List<String> correspondent;
    private List<ContentFile> baseFiles;
    private List<MissiveFile> missiveFiles;
    private Integer totalCount;
    private String number;
    private LocalDate registeredAt;

    public MissiveListDTOBuilder setID(UUID ID) {
        this.ID = ID;
        return this;
    }

    public MissiveListDTOBuilder setOrgID(Long orgID) {
        this.orgID = orgID;
        return this;
    }

    public MissiveListDTOBuilder setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public MissiveListDTOBuilder setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
        return this;
    }

    public MissiveListDTOBuilder setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
        return this;
    }

    public MissiveListDTOBuilder setConfirmatives(List<ConfirmativeShortInfoDTO> confirmatives) {
        this.confirmatives = confirmatives;
        return this;
    }

    public MissiveListDTOBuilder setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
        return this;
    }

    public MissiveListDTOBuilder setCorrespondent(List<String> correspondent) {
        this.correspondent = correspondent;
        return this;
    }

    public MissiveListDTOBuilder setBaseFiles(List<ContentFile> baseFiles) {
        this.baseFiles = baseFiles;
        return this;
    }

    public MissiveListDTOBuilder setMissiveFiles(List<MissiveFile> missiveFiles) {
        this.missiveFiles = missiveFiles;
        return this;
    }

    public MissiveListDTOBuilder setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public MissiveListDTOBuilder setNumber(String number) {
        this.number = number;
        return this;
    }

    public MissiveListDTOBuilder setRegisteredAt(LocalDate registeredAt) {
        this.registeredAt = registeredAt;
        return this;
    }

    public MissiveListDTO create() {
        return new MissiveListDTO(ID, orgID, departmentName, senderFirstName, senderLastName, confirmatives, shortInfo, correspondent, baseFiles, totalCount, number, registeredAt);
    }
}
