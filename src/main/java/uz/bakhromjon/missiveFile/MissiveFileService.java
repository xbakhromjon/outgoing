package uz.bakhromjon.missiveFile;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.missiveFile.dto.MissiveFileCreateDTO;
import uz.bakhromjon.outReceiver.OutReceiverValidator;
import uz.bakhromjon.missiveFile.MissiveFile.MissiveFileBuilder;
import uz.bakhromjon.utils.BaseUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MissiveFileService extends AbstractService<MissiveFileRepository, OutReceiverValidator, MissiveFileMapper> {
    private final ContentFileService contentFileService;
    private final BaseUtils baseUtils;

    public MissiveFileService(MissiveFileRepository repository, OutReceiverValidator validator, MissiveFileMapper mapper,
                              ContentFileService contentFileService, BaseUtils baseUtils) {
        super(repository, validator, mapper);
        this.contentFileService = contentFileService;
        this.baseUtils = baseUtils;
    }

    public List<MissiveFile> refresh(UUID missiveFileID, String content) throws IOException {
//        MissiveFile missiveFile = getPersist(missiveFileID);
//        missiveFile.setContent(content);
//        String filePath = missiveFile.getFile().getPath();
//        baseUtils.writeHtmlAsPdf(filePath, content);
        repository.updateContent(missiveFileID, content);
//        List<MissiveFile> newMissiveFiles = create(content);
//        deleteAll(trashMissiveFiles);
        return new ArrayList<>();
    }

    public List<MissiveFile> create(String content) throws IOException {
        MissiveFile missiveFile = mapper.toEntity(content);
        return repository.saveAll(Collections.singletonList(missiveFile));
    }

    public void deleteAll(List<MissiveFile> missiveFiles) {
        List<UUID> IDs = missiveFiles.stream().map(MissiveFile::getId).collect(Collectors.toList());
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

    public MissiveFile getPersist(UUID ID) {
        Optional<MissiveFile> optional = repository.find(ID);
        return optional.orElseThrow(() -> {
            throw new UniversalException("MissiveFile not found", HttpStatus.BAD_REQUEST);
        });
    }

    public MissiveFile getLastVersion(UUID missiveID) {
        Optional<MissiveFile> optional = repository.findLastVersion(missiveID);
        return optional.orElseThrow(() -> {
            throw new UniversalException("MissiveFile last version not found", HttpStatus.BAD_REQUEST);
        });
    }

    public MissiveFile save(MissiveFile missiveFile) {
        return repository.save(missiveFile);
    }
}
