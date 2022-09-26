package uz.darico.missiveFile;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.Confirmative;
import uz.darico.contentFile.ContentFile;
import uz.darico.missive.Missive;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.OutReceiverMapper;
import uz.darico.outReceiver.OutReceiverRepository;
import uz.darico.outReceiver.OutReceiverValidator;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class MissiveFileService extends AbstractService<MissiveFileRepository, OutReceiverValidator, MissiveFileMapper> {
    public MissiveFileService(MissiveFileRepository repository, OutReceiverValidator validator, MissiveFileMapper mapper) {
        super(repository, validator, mapper);
    }

    public List<MissiveFile> refresh(ContentFile missiveFileContent, List<MissiveFile> trashMissiveFiles) {
        List<MissiveFile> newMissiveFiles = create(missiveFileContent);
        deleteAll(trashMissiveFiles);
        return newMissiveFiles;
    }

    public List<MissiveFile> create(ContentFile contentFile) {
        MissiveFile missiveFile = mapper.toEntity(contentFile);
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
}
