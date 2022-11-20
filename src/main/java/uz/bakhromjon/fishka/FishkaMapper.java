package uz.bakhromjon.fishka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.fishka.dto.FishkaCreateDTO;
import uz.bakhromjon.fishka.dto.FishkaGetDTO;
import uz.bakhromjon.fishka.enums.FishkaType;

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
