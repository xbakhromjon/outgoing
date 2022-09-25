package uz.darico.contentFile;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.Confirmative;
import uz.darico.exception.exception.UniversalException;
import uz.darico.inReceiver.InReceiverValidator;
import uz.darico.utils.EntityGetter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContentFileService extends AbstractService<ContentFileRepository, InReceiverValidator, ContentFileMapper> {

    public ContentFileService(ContentFileRepository repository, InReceiverValidator validator, ContentFileMapper mapper) {
        super(repository, validator, mapper);
    }


    public List<ContentFile> refresh(List<UUID> baseFileIDs, List<ContentFile> trashContentFiles) {
        List<ContentFile> newBaseFiles = repository.findByIDs(baseFileIDs);
        deleteAll(trashContentFiles);
        return newBaseFiles;
    }


    public void deleteAll(List<ContentFile> contentFiles) {
        List<UUID> IDs = contentFiles.stream().map(ContentFile::getId).toList();
        repository.deleteFromRelatedTable(IDs);
        repository.deleteAll(IDs);
    }

    public List<ContentFile> getContentFiles(List<UUID> baseFileIDs) {
        return repository.findByIDs(baseFileIDs);
    }

    public ContentFile getContentFile(UUID ID) {
        Optional<ContentFile> optional = repository.findById(ID);
        if (optional.isEmpty()) {
            ContentFile saved = repository.save(new ContentFile());
            return saved;
        } else {
            return optional.get();
        }
    }

    public List<ContentFile> saveAll(List<ContentFile> contentFiles) {
        return repository.saveAll(contentFiles);
    }
}
