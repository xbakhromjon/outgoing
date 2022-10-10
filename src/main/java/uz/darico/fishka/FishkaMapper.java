package uz.darico.fishka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.feign.UserFeignService;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.feign.obj.UserInfo;
import uz.darico.fishka.dto.FishkaCreateDTO;
import uz.darico.fishka.dto.FishkaGetDTO;
import uz.darico.fishka.enums.FishkaType;
import uz.darico.sender.Sender;
import uz.darico.sender.dto.SenderGetDTO;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FishkaMapper implements BaseMapper {

    public Fishka toEntity(FishkaCreateDTO createDTO) {
        return new Fishka.FishkaBuilder().fishkaTypeCode(createDTO.getFishkaType()).isVisible(createDTO.getIsVisible()).orgID(createDTO.getOrgID()).
                build();
    }

    public FishkaGetDTO toGetDTO(Fishka fishka) {
        return new FishkaGetDTO(fishka.getId(), FishkaType.toFishkaType(fishka.getFishkaTypeCode()), fishka.getFile(), fishka.getIsVisible()
        );
    }

    public List<FishkaGetDTO> toGetDTO(List<Fishka> fishkaList) {
        List<FishkaGetDTO> getDTOs = new ArrayList<>();
        for (Fishka fishka : fishkaList) {
            FishkaGetDTO fishkaGetDTO = toGetDTO(fishka);
            getDTOs.add(fishkaGetDTO);
        }
        return getDTOs;
    }
}
