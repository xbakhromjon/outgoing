package uz.darico.missiveFile;

import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;
import uz.darico.missive.Missive;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.OutReceiverMapper;
import uz.darico.outReceiver.OutReceiverRepository;
import uz.darico.outReceiver.OutReceiverValidator;

import java.util.List;
import java.util.UUID;

@Service
public class MissiveFileService extends AbstractService<MissiveFileRepository, OutReceiverValidator, MissiveFileMapper> {
    public MissiveFileService(MissiveFileRepository repository, OutReceiverValidator validator, MissiveFileMapper mapper) {
        super(repository, validator, mapper);
    }

    public void deleteAll(List<MissiveFile> missiveFiles) {
        List<UUID> IDs = missiveFiles.stream().map(MissiveFile::getId).toList();
        repository.deleteAll(IDs);
    }
}
