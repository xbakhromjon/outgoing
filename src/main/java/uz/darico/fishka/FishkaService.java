package uz.darico.fishka;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.contentFile.ContentFileService;
import uz.darico.exception.exception.UniversalException;
import uz.darico.fishka.dto.FishkaCreateDTO;
import uz.darico.fishka.dto.FishkaUpdateDTO;
import uz.darico.sender.Sender;

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
        Fishka fishka = mapper.toEntity(createDTO);
        fishka.setFile(contentFileService.getPersist(createDTO.getFileID()));
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
}