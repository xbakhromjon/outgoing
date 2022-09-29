package uz.darico.missiveFile;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.missiveFile.dto.MissiveFileCreateDTO;
import uz.darico.outReceiver.OutReceiverValidator;
import uz.darico.missiveFile.MissiveFile.MissiveFileBuilder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class MissiveFileService extends AbstractService<MissiveFileRepository, OutReceiverValidator, MissiveFileMapper> {
    private final ContentFileService contentFileService;

    public MissiveFileService(MissiveFileRepository repository, OutReceiverValidator validator, MissiveFileMapper mapper,
                              ContentFileService contentFileService) {
        super(repository, validator, mapper);
        this.contentFileService = contentFileService;
    }

    public List<MissiveFile> refresh(String content, List<MissiveFile> trashMissiveFiles) {
        List<MissiveFile> newMissiveFiles = create(content);
        deleteAll(trashMissiveFiles);
        return newMissiveFiles;
    }

    public List<MissiveFile> create(String content) {
        MissiveFile missiveFile = mapper.toEntity(content);
        return repository.saveAll(Collections.singletonList(missiveFile));
    }

    public void deleteAll(List<MissiveFile> missiveFiles) {
        List<UUID> IDs = missiveFiles.stream().map(MissiveFile::getId).toList();
        repository.deleteAll(IDs);
    }

    public List<MissiveFile> saveAll(List<MissiveFile> missiveFiles) {
        return repository.saveAll(missiveFiles);
    }

    public List<MissiveFile> getAll(UUID ID) {
        return repository.getAll(ID);
    }

    public MissiveFile createNewVersion(MissiveFileCreateDTO createDTO) {
        UUID missiveID = createDTO.getMissiveID();
        Integer maxVersion = repository.getMaxVersion(missiveID);
        MissiveFile missiveFile = new MissiveFileBuilder().content(createDTO.getContent()).version(maxVersion + 1).build();
        return repository.save(missiveFile);
    }

    public void delete(UUID missiveFileID) {
        repository.delete(missiveFileID);
        repository.deleteFromRelatedTables(missiveFileID);
    }
}
