package uz.darico.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.darico.confirmative.dto.ConfirmativeShortInfoDTO;
import uz.darico.contentFile.ContentFile;
import uz.darico.missiveFile.MissiveFile;

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

    public MissiveListDTO create() {
        return new MissiveListDTO(ID, orgID, departmentName, senderFirstName, senderLastName, confirmatives, shortInfo, correspondent, baseFiles, totalCount);
    }
}
