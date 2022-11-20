package uz.bakhromjon.journal;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.base.controller.AbstractController;
import uz.bakhromjon.journal.dto.BaseOrderDTO;
import uz.bakhromjon.journal.dto.JournalCreateDto;
import uz.bakhromjon.journal.dto.JournalUpdateDto;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/journal")
public class JournalController extends AbstractController<JournalService> {

    private final String DEFAULT_PAGE_NUMBER = "0";
    private final String DEFAULT_PAGE_SIZE = "20";

    public JournalController(JournalService service) {
        super(service);
    }

    @PostMapping()
    public HttpEntity<?> create(@RequestBody JournalCreateDto createDto, HttpServletRequest request) throws JsonProcessingException {
        return service.create(createDto, request);
    }

    @DeleteMapping("/{ID}")
    public HttpEntity<?> delete(@PathVariable UUID ID)  {
        return service.delete(ID);
    }

    @PatchMapping()
    public HttpEntity<?> update(@RequestBody JournalUpdateDto updateDto) {
        return service.update(updateDto);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> get(@PathVariable String id) {
        return service.get(id);
    }


    @GetMapping("/archiveds/{orgId}")
    public HttpEntity<?> getArchiveds(@PathVariable Long orgId, @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
                                      @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size) throws JsonProcessingException {
        return service.getArchiveds(orgId, PageRequest.of(page, size));
    }

    @GetMapping("/getOrgAll/{id}")
    public HttpEntity<?> getOrgAll(@PathVariable Long id) throws JsonProcessingException {
        return service.getOrgAll(id);
    }

    @PatchMapping("/orderNumber")
    public HttpEntity<?> setOrderNumber(@RequestBody BaseOrderDTO baseOrderDTO) {
        return service.setOrderNumber(baseOrderDTO);
    }

    @PatchMapping("/close/{id}")
    public HttpEntity<?> close(@PathVariable String id) {
        return service.close(id);
    }

    @PatchMapping("/open/{id}")
    public HttpEntity<?> open(@PathVariable String id) {
        return service.open(id);
    }

    @PatchMapping("/archive/{id}")
    public HttpEntity<?> archive(@PathVariable String id) {
        return service.archive(id);
    }

    @PatchMapping("/unArchive/{id}")
    public HttpEntity<?> unArchive(@PathVariable String id) {
        return service.unArchive(id);
    }


    @GetMapping("/active/{orgId}")
    public HttpEntity<?> getOrgJournal(@PathVariable Long orgId, @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
                                       @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size) throws JsonProcessingException {
        return service.getActiveJournal(orgId, PageRequest.of(page, size));
    }

    @GetMapping("/logs/{id}")
    public HttpEntity<?> getLogs(@PathVariable String id) throws JsonProcessingException {
        return service.getLogs(id);
    }

    @GetMapping("/closeds/{orgId}")
    public HttpEntity<?> getCloseds(@PathVariable Long orgId, @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
                                    @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size) throws JsonProcessingException {
        return service.getCloseds(orgId, PageRequest.of(page, size));
    }

    @GetMapping("/moduleJournal")
    public HttpEntity<?> getJournalsByModule() {
        return service.getJournalsByModule();
    }
}





