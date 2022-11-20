package uz.bakhromjon.outReceiver;

import org.springframework.stereotype.Service;
import uz.bakhromjon.base.entity.AbstractEntity;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.email.EmailSenderService;
import uz.bakhromjon.email.dto.EmailSenderDTO;
import uz.bakhromjon.missive.Missive;
import uz.bakhromjon.outReceiver.dto.OutReceiverCreateDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        List<UUID> IDs = outReceivers.stream().map(AbstractEntity::getId).collect(Collectors.toList());
        repository.deleteFromRelatedTable(IDs);
        repository.deleteAll(IDs);
    }

    public List<OutReceiver> saveAll(List<OutReceiver> outReceivers) {
        return repository.saveAll(outReceivers);
    }

    public List<OutReceiver> getAllByMissiveID(UUID ID) {
        return repository.getAllByMissiveID(ID);
    }

    public void send(Missive missive) {
        List<OutReceiver> outReceivers = missive.getOutReceivers();
        for (OutReceiver outReceiver : outReceivers) {
            if (outReceiver.getCorrespondentEmail() != null) {
                EmailSenderDTO emailSenderDTO = new EmailSenderDTO(outReceiver.getCorrespondentEmail(), from, subject, missive.getShortInfo(), missive.getReadyPDF().getPath());
                emailSenderService.send(emailSenderDTO);
            }
        }
    }
}
