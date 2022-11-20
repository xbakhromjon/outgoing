package uz.bakhromjon.journal;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.AbstractMapper;
import uz.bakhromjon.journal.dto.JournalCreateDto;
import uz.bakhromjon.journal.dto.JournalDto;
import uz.bakhromjon.journal.dto.JournalSelectDTO;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface JournalMapper extends AbstractMapper {


    default List<JournalSelectDTO> toSelectDTO(List<Journal> journalList) {
        List<JournalSelectDTO> journalSelectDTOs = new ArrayList<>();
        for (Journal journal : journalList) {
            JournalSelectDTO journalSelectDTO = new JournalSelectDTO();
            journalSelectDTO.setUzName(journal.getUzName());
            journalSelectDTO.setRuName(journal.getRuName());
            journalSelectDTO.setCurrentNumber(journal.getCurrentNumber());
            journalSelectDTO.setId(journal.getId());
            journalSelectDTOs.add(journalSelectDTO);
        }

        return journalSelectDTOs;
    }

    Journal fromCreateDto(JournalCreateDto createDto);

    JournalDto toDto(Journal journal);

    List<JournalDto> toDto(List<Journal> items);
}
