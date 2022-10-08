package uz.darico.outReceiver;

import lombok.Value;
import org.springframework.stereotype.Service;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.service.AbstractService;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.ConfirmativeMapper;
import uz.darico.confirmative.ConfirmativeRepository;
import uz.darico.confirmative.ConfirmativeValidator;
import uz.darico.email.EmailSenderService;
import uz.darico.email.dto.EmailSenderDTO;
import uz.darico.missiveFile.MissiveFile;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;

import java.util.List;
import java.util.UUID;

@Service
public class OutReceiverService extends AbstractService<OutReceiverRepository, OutReceiverValidator, OutReceiverMapper> {
    private final EmailSenderService emailSenderService;

    private final String from = "xbakhromjon@gmail.com";
    private final String subject = "D-DOC dasturdan siz uchun yangi xat mavjud";
    private final String text = "D-DOC dasturdan siz uchun yangi xat mavjud";

    public OutReceiverService(OutReceiverRepository repository, OutReceiverValidator validator, OutReceiverMapper mapper,
                              EmailSenderService emailSenderService) {
        super(repository, validator, mapper);
        this.emailSenderService = emailSenderService;
    }

    public List<OutReceiver> refresh(List<OutReceiverCreateDTO> outReceiverCreateDTOs, List<OutReceiver> trashOutReceivers) {
        List<OutReceiver> newOutReceivers = create(outReceiverCreateDTOs);
        deleteAll(trashOutReceivers);
        return newOutReceivers;
    }


    public List<OutReceiver> create(List<OutReceiverCreateDTO> outReceiverCreateDTOs) {
        List<OutReceiver> outReceivers = mapper.toEntity(outReceiverCreateDTOs);
        return repository.saveAll(outReceivers);
    }

    public void deleteAll(List<OutReceiver> outReceivers) {
        List<UUID> IDs = outReceivers.stream().map(AbstractEntity::getId).toList();
        repository.deleteFromRelatedTable(IDs);
        repository.deleteAll(IDs);
    }

    public List<OutReceiver> saveAll(List<OutReceiver> outReceivers) {
        return repository.saveAll(outReceivers);
    }

    public List<OutReceiver> getAllByMissiveID(UUID ID) {
        return repository.getAllByMissiveID(ID);
    }

    public void send(UUID ID, MissiveFile lastVersion) {
        List<OutReceiver> outReceivers = getAllByMissiveID(ID);
        for (OutReceiver outReceiver : outReceivers) {
            if (outReceiver.getCorrespondentEmail() != null) {
                EmailSenderDTO emailSenderDTO = new EmailSenderDTO(outReceiver.getCorrespondentEmail(), from, subject, text, lastVersion.getFile().getPath());
                emailSenderService.send(emailSenderDTO);
            }
        }
    }
}
