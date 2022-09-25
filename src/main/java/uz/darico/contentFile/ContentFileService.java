package uz.darico.contentFile;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.InReceiverMapper;
import uz.darico.inReceiver.InReceiverRepository;
import uz.darico.inReceiver.InReceiverValidator;

import java.util.List;
import java.util.UUID;

@Service
public class ContentFileService extends AbstractService<ContentFileRepository, InReceiverValidator, ContentFileMapper> {

    public ContentFileService(ContentFileRepository repository, InReceiverValidator validator, ContentFileMapper mapper) {
        super(repository, validator, mapper);
    }

    public void deleteAll(List<ContentFile> contentFiles) {
        List<UUID> IDs = contentFiles.stream().map(ContentFile::getId).toList();
//        repository.deleteAll(IDs);
    }

    public List<ContentFile> getContentFiles(List<UUID> baseFileIDs) {
        return List.of(new ContentFile());
    }

    public ContentFile getContentFile(UUID missiveFileID) {
        return new ContentFile();
    }
}
