package uz.darico.missive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.darico.base.controller.AbstractController;
import uz.darico.missive.dto.ContentUpdateDTO;
import uz.darico.missive.dto.MissiveCreateDTO;
import uz.darico.missive.dto.MissiveRejectDTO;
import uz.darico.missive.dto.MissiveUpdateDTO;
import uz.darico.missiveFile.dto.MissiveFileCreateDTO;
import uz.darico.utils.SearchDTO;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/missive")
public class MissiveController extends AbstractController<MissiveService> {
    public MissiveController(MissiveService service) {
        super(service);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody MissiveCreateDTO createDTO) throws IOException {
        return service.create(createDTO);
    }

    @PatchMapping()
    public ResponseEntity<?> update(@RequestBody MissiveUpdateDTO updateDTO) throws IOException {
        return service.update(updateDTO);
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "id") String id) {
        return service.delete(id);
    }

    @GetMapping("/{workPlaceID}/{id}")
    public ResponseEntity<?> get(@PathVariable Long workPlaceID, @PathVariable(name = "id") String id) {
        return service.get(workPlaceID, id);
    }

    @GetMapping("/raw/{id}")
    public ResponseEntity<?> getRaw(@PathVariable(name = "id") UUID id) {
        return service.getRaw(id);
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
    public ResponseEntity<?> createNewVersion(@RequestBody MissiveCreateDTO createDTO) throws IOException {
        return service.createNewVersion(createDTO);
    }

    @DeleteMapping("/version")
    public ResponseEntity<?> deleteVersion(@RequestParam UUID missiveFileID) {
        return service.deleteVersion(missiveFileID);
    }

    @GetMapping("/sketchy/{workPlaceID}/{id}")
    public ResponseEntity<?> getSketchy(@PathVariable Long workPlaceID, @PathVariable(name = "id") String id) {
        return service.getSketchy(workPlaceID, id);
    }

    @GetMapping("/count/{workPlaceID}")
    public ResponseEntity<?> getCount(@PathVariable Long workPlaceID) {
        return service.getCount(workPlaceID);
    }

    @PatchMapping("/content")
    public ResponseEntity<?> updateContent(@RequestBody ContentUpdateDTO contentUpdateDTO) {
        return service.updateContent(contentUpdateDTO);
    }

}
