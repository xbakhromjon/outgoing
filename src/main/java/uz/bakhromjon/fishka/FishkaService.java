package uz.bakhromjon.fishka;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.contentFile.ContentFile;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.fishka.dto.FishkaCreateDTO;
import uz.bakhromjon.fishka.dto.FishkaGetDTO;
import uz.bakhromjon.fishka.dto.FishkaUpdateDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FishkaService extends AbstractService<FishkaRepository, FishkaValidator, FishkaMapper> {
    private final ContentFileService contentFileService;

    public FishkaService(FishkaRepository repository, FishkaValidator validator, FishkaMapper mapper,
                         ContentFileService contentFileService) {
        super(repository, validator, mapper);
        this.contentFileService = contentFileService;
    }


    public ResponseEntity<?> create(FishkaCreateDTO createDTO) {
        validator.validForCreate(createDTO);
        Fishka fishka = getPersist(createDTO.getFishkaType());
        if (fishka != null) {
            ContentFile file = contentFileService.getPersist(createDTO.getFileID());
            fishka.setFile(file);
            fishka.setIsVisible(createDTO.getIsVisible());
        } else {
            fishka = mapper.toEntity(createDTO);
            fishka.setFile(contentFileService.getPersist(createDTO.getFileID()));
        }
        Fishka saved = repository.save(fishka);
        return ResponseEntity.ok(mapper.toGetDTO(saved));
    }

    public ResponseEntity<?> update(FishkaUpdateDTO updateDTO) {
        validator.validForUpdate(updateDTO);
        Fishka fishka = getPersist(updateDTO.getId());
        fishka.setIsVisible(updateDTO.getIsVisible());
        fishka.setFile(contentFileService.getPersist(updateDTO.getFileID()));
        Fishka saved = repository.save(fishka);
        return ResponseEntity.ok(mapper.toGetDTO(saved));
    }

    public ResponseEntity<?> delete(UUID ID) {
        repository.delete(ID);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> get(UUID ID) {
        Fishka fishka = getPersist(ID);
        return ResponseEntity.ok(mapper.toGetDTO(fishka));
    }

    public ResponseEntity<?> list(Long orgID) {
        List<Fishka> list = repository.findAll(orgID);
        return ResponseEntity.ok(mapper.toGetDTO(list));
    }

    public Fishka getPersist(UUID ID) {
        Optional<Fishka> optional = repository.find(ID);
        return optional.orElseThrow(() -> {
            throw new UniversalException("Fishka not found", HttpStatus.BAD_REQUEST);
        });
    }


    public Fishka getPersist(Integer fishkaType) {
        Optional<Fishka> optional = repository.findByFishkaType(fishkaType);
        return optional.orElse(null);
    }

    public Fishka getDefault(Long orgID) {
        List<Fishka> list = repository.findAll(orgID);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public FishkaGetDTO getFishkaGetDTO(UUID fishkaID) {
        Fishka fishka = getPersist(fishkaID);
        return mapper.toGetDTO(fishka);
    }
}
