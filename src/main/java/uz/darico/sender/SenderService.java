package uz.darico.sender;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;
import uz.darico.exception.exception.UniversalException;

import java.util.Optional;
import java.util.UUID;

@Service
public class SenderService extends AbstractService<SenderRepository, SenderValidator, SenderMapper> {
    public SenderService(SenderRepository repository, SenderValidator validator, SenderMapper mapper) {
        super(repository, validator, mapper);
    }

    public Sender save(Sender sender) {
        return repository.save(sender);
    }

    public Sender getPersistByMissiveID(UUID ID) {
        Optional<Sender> optional = repository.findById(ID);
        return optional.orElseThrow(() -> {
            throw new UniversalException("Sender Not Found", HttpStatus.BAD_REQUEST);
        });
    }

    public void notReady(UUID id) {

    }
}
