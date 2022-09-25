package uz.darico.sender;

import org.springframework.stereotype.Service;
import uz.darico.base.service.AbstractService;

@Service
public class SenderService extends AbstractService<SenderRepository, SenderValidator, SenderMapper> {
    public SenderService(SenderRepository repository, SenderValidator validator, SenderMapper mapper) {
        super(repository, validator, mapper);
    }

    public Sender save(Sender sender) {
        return repository.save(sender);
    }
}
