package uz.darico.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileRepository;
import uz.darico.exception.exception.UniversalException;
import uz.darico.missive.Missive;
import uz.darico.missive.MissiveMapper;
import uz.darico.missive.MissiveRepository;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EntityGetter {
    private final MissiveRepository missiveRepository;
    private final ContentFileRepository contentFileRepository;

    public EntityGetter(MissiveRepository missiveRepository, ContentFileRepository contentFileRepository) {
        this.missiveRepository = missiveRepository;
        this.contentFileRepository = contentFileRepository;
    }


    public List<ContentFile> getContentFiles(List<UUID> IDs) {
        return contentFileRepository.findByIDs(IDs);
    }
}
