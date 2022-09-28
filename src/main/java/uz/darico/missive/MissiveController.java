package uz.darico.missive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.darico.base.controller.AbstractController;
import uz.darico.missive.dto.MissiveCreateDTO;
import uz.darico.missive.dto.MissiveRejectDTO;
import uz.darico.missive.dto.MissiveUpdateDTO;
import uz.darico.missiveFile.dto.MissiveFileCreateDTO;
import uz.darico.utils.SearchDTO;

import java.util.UUID;

@RestController
@RequestMapping("/missive")
public class MissiveController extends AbstractController<MissiveService> {
    public MissiveController(MissiveService service) {
        super(service);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody MissiveCreateDTO createDTO) {
        return service.create(createDTO);
    }


    @PatchMapping()
    public ResponseEntity<?> update(@RequestBody MissiveUpdateDTO updateDTO) {
        return service.update(updateDTO);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam(name = "id") String id) {
        return service.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(name = "id") String id) {
        return service.get(id);
    }

    @PatchMapping("/ready-sender")
    public ResponseEntity<?> readyForSender(@RequestParam(name = "id") String id) {
        return service.readyForSender(id);
    }

    @PatchMapping("/ready-conf")
    public ResponseEntity<?> readyForConf(@RequestParam(name = "conf") String confId) {
        return service.readyForConf(confId);
    }

    @PatchMapping("/sign")
    public ResponseEntity<?> sign(@RequestParam(name = "id") String id) {
        return service.sign(id);
    }

    @PatchMapping("/reject")
    public ResponseEntity<?> reject(@RequestBody MissiveRejectDTO rejectDTO) {
        return service.reject(rejectDTO);
    }

    @PostMapping("/list")
    public ResponseEntity<?> getList(@RequestBody SearchDTO searchDTO) {
         return service.getList(searchDTO);
    }

    @PostMapping("/version")
    public ResponseEntity<?> createNewVersion(@RequestBody MissiveFileCreateDTO createDTO) {
         return service.createNewVersion(createDTO);
    }

    @DeleteMapping("/version")
    public ResponseEntity<?> deleteVersion(@RequestParam UUID missiveFileID) {
         return service.deleteVersion(missiveFileID);
    }

}
