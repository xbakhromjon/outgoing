package uz.bakhromjon.journal;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.feign.UserFeignService;
import uz.bakhromjon.feign.obj.UserInfo;
import uz.bakhromjon.journal.dto.*;
import uz.bakhromjon.missive.MissiveService;
import uz.bakhromjon.utils.BaseUtils;
import uz.bakhromjon.utils.ResponsePage;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JournalService extends AbstractService<JournalRepository, JournalValidator, JournalMapper> {

    private final BaseUtils baseUtils;
    private final JournalMapper mapper;
    private final MissiveService missiveService;
    private final UserFeignService userFeignService;

    public JournalService(JournalRepository repository, JournalValidator validator, BaseUtils baseUtils, JournalMapper journalMapper, MissiveService missiveService,
                          UserFeignService userFeignService) {
        super(repository, validator, journalMapper);
        this.baseUtils = baseUtils;
        this.mapper = journalMapper;
        this.missiveService = missiveService;
        this.userFeignService = userFeignService;
    }

    public HttpEntity<?> create(JournalCreateDto createDto, HttpServletRequest request) throws JsonProcessingException {
        UUID orgId = createDto.getOrgId();
        String name = createDto.getUzName();
        boolean journalExists = repository.existsByOrgIdAndUzNameIgnoreCaseAndIsDeleted(orgId, name, false);
        if (journalExists) {
            return new ResponseEntity<>("Jurnal allaqachon qo'shilgan.", HttpStatus.NOT_FOUND);
        }
        Journal journal = mapper.fromCreateDto(createDto);
        journal.setCurrentNumber(journal.getBeginNumber());
        journal.setUserID(createDto.getUserID());
        journal.setCreatedAt(LocalDateTime.now());
        journal.setCreatedIPAddress(baseUtils.getClientIPAddress(request));
        journal.setCreatedDevice(baseUtils.getClientOS(request));
        journal.setCreatedBrowser(baseUtils.getClientBrowser(request));
        journal.setOrgId(orgId);
        Integer orderNumber = repository.findOrderNumber(orgId);
        journal.setOrderNumber(orderNumber);
        repository.save(journal);
        return new ResponseEntity<>("Successfully", HttpStatus.OK);
    }


    public HttpEntity<?> delete(UUID ID) {
        validator.validOnKey(ID);
        Journal journal = getPersist(ID);
        Integer count = missiveService.getCountByJournal(ID);
        if (count != 0) {
            return new ResponseEntity<>("Bu jurnalda hali yopilmagan hujjatlar bor.", HttpStatus.BAD_REQUEST);
        }
        journal.setDeleted(true);
        repository.save(journal);
        return ResponseEntity.ok(ID);
    }

    public HttpEntity<?> update(JournalUpdateDto updateDto) {
        Journal journal = getPersist(updateDto.getID());
        if (journal.getBeginNumber() > updateDto.getBeginNumber()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Jurnal nomeri avvalgisidan kichik bo'lishi mumkin emas");
        }
        journal.setUpdatedAt(LocalDateTime.now());
        journal.setJournalPrefix(updateDto.getJournalPrefix());
        journal.setJournalPostfix(updateDto.getJournalPostfix());
        journal.setShortDescription(updateDto.getShortDescription());
        journal.setBeginNumber(updateDto.getBeginNumber());
        journal.setCurrentNumber(updateDto.getBeginNumber());
        journal.setRuName(updateDto.getRuName());
        journal.setUzName(updateDto.getUzName());
        Journal save = repository.save(journal);
        return ResponseEntity.ok(mapper.toDto(save));
    }

    public HttpEntity<?> get(String id) {
        UUID ID = validator.validOnKey(id);
        Journal journal = getPersist(ID);
        JournalDto journalDto = mapper.toDto(journal);
        return ResponseEntity.ok(journalDto);
    }


    public ResponseEntity<?> setOrderNumber(BaseOrderDTO baseOrderDTO) {
        List<OrderDTO> orderDTOs = baseOrderDTO.getOrders();
        List<Journal> items = new ArrayList<>();
        for (OrderDTO orderDTO : orderDTOs) {
            Journal item = repository.changeOrderNumber(orderDTO.getId(), orderDTO.getOrder());
            items.add(item);
        }
        List<JournalDto> result = mapper.toDto(items);
        return ResponseEntity.ok(result);
    }

    public HttpEntity<?> close(String id) {
        UUID ID = validator.validOnKey(id);
        Optional<Journal> journalOptional = repository.findByIdAndIsDeletedAndClosed(ID, false, false);
        if (journalOptional.isEmpty()) {
            return new ResponseEntity<>("Journal Not Found", HttpStatus.NOT_FOUND);
        }
        Journal journal = journalOptional.get();
        journal.setClosed(true);
        Journal save = repository.save(journal);
        return new ResponseEntity<>(mapper.toDto(save), HttpStatus.OK);
    }

    public HttpEntity<?> archive(String id) {
        UUID ID = validator.validOnKey(id);
        Optional<Journal> journalOptional = repository.findByIdAndArchivedAndIsDeleted(ID, false, false);
        if (journalOptional.isEmpty()) {
            return new ResponseEntity<>("Journal Not Found", HttpStatus.NOT_FOUND);
        }
        Journal journal = journalOptional.get();
        journal.setArchived(true);
        repository.save(journal);
        return new ResponseEntity<>("Successfully Archived", HttpStatus.OK);
    }


    public HttpEntity<?> getArchiveds(Long orgId, Pageable pageable) {
        Page<Journal> page = repository.findAllByArchivedAndIsDeletedAndOrgId(true, false, orgId, pageable);
        List<Journal> journals = page.getContent();
        List<JournalDto> content = mapper.toDto(journals);
        ResponsePage<JournalDto> responsePage = baseUtils.toResponsePage(page, content);
        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    public HttpEntity<?> getActiveJournal(Long orgId, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;
        List<Journal> journals = repository.findAllByIsDeletedAndClosedAndArchivedAndOrgId(orgId, pageSize, offset);
        Page<Journal> page = repository.findAllByIsDeletedAndArchivedAndOrgId(false, false, orgId, pageable);
        List<JournalDto> content = mapper.toDto(journals);
        ResponsePage<JournalDto> responsePage = baseUtils.toResponsePage(page, content);
        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    public HttpEntity<?> getLogs(String id) {
        UUID ID = validator.validOnKey(id);
        Journal journal = getPersist(ID);
        UUID userID = journal.getUserID();
        UserInfo userInfo = userFeignService.getUserInfo(userID);
        JournalLogDto journalLogDto = new JournalLogDto();
        if (userInfo != null) {
            journalLogDto.setFirstName(userInfo.getFirstName());
            journalLogDto.setLastName(userInfo.getLastName());
            journalLogDto.setMiddleName(userInfo.getMiddleName());
        }
        journalLogDto.setCreatedIpAddress(journal.getCreatedIPAddress());
        journalLogDto.setCreatedBrowser(journal.getCreatedBrowser());
        journalLogDto.setCreatedDevice(journal.getCreatedDevice());
        journalLogDto.setCreatedAt(journal.getCreatedAt());
        return new ResponseEntity<>(journalLogDto, HttpStatus.OK);
    }


    public HttpEntity<?> unArchive(String id) {
        UUID ID = validator.validOnKey(id);
        Optional<Journal> journalOptional = repository.findByIdAndArchivedAndIsDeleted(ID, true, false);
        if (journalOptional.isEmpty()) {
            return new ResponseEntity<>("Jurnal arxivda emas.", HttpStatus.BAD_REQUEST);
        }
        Journal journal = journalOptional.get();
        journal.setArchived(false);
        repository.save(journal);
        return new ResponseEntity<>("Successfully UnArchived", HttpStatus.OK);
    }

    public HttpEntity<?> open(String id) {
        UUID ID = validator.validOnKey(id);
        Optional<Journal> journalOptional = repository.findByIdAndIsDeletedAndClosed(ID, false, true);
        if (journalOptional.isEmpty()) {
            return new ResponseEntity<>("Jurnal yopilmagan", HttpStatus.BAD_REQUEST);
        }
        Journal journal = journalOptional.get();
        journal.setClosed(false);
        Journal save = repository.save(journal);
        return new ResponseEntity<>(mapper.toDto(save), HttpStatus.OK);
    }

    public HttpEntity<?> getCloseds(Long orgId, Pageable pageable) {
        Page<Journal> page = repository.findAllByOrgIdAndDeletedAndClosed(orgId, false, true, pageable);
        List<Journal> journals = page.getContent();
        List<JournalDto> content = mapper.toDto(journals);
        ResponsePage<JournalDto> responsePage = baseUtils.toResponsePage(page, content);
        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    public HttpEntity<?> getOrgAll(Long orgId) {
        List<Journal> journals = repository.findAllByArchivedAndIsDeletedAndClosedAndOrgId(false, false, false, orgId);
        return new ResponseEntity<>(mapper.toSelectDTO(journals), HttpStatus.OK);
    }

    public ResponseEntity<?> getJournalsByModule() {
        List<Journal> journals = repository.findAllByClosedAndIsDeleted(false, false);
        List<JournalDto> journalDtos = mapper.toDto(journals);
        return ResponseEntity.ok(journalDtos);
    }


    public Journal getPersist(UUID ID) {
        Optional<Journal> optional = repository.find(ID);
        return optional.orElseThrow(() -> {
            throw new UniversalException("Journal not found", HttpStatus.BAD_REQUEST);
        });
    }
}
