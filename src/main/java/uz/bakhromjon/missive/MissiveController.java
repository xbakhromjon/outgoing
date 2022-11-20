package uz.bakhromjon.missive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.base.controller.AbstractController;
import uz.bakhromjon.missive.dto.*;
import uz.bakhromjon.utils.SearchDTO;

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
    public ResponseEntity<?> get(@PathVariable UUID workPlaceID, @PathVariable(name = "id") String id) {
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
    public ResponseEntity<?> getSketchy(@PathVariable UUID workPlaceID, @PathVariable(name = "id") String id) {
        return service.getSketchy(workPlaceID, id);
    }

    @GetMapping("/count/{workPlaceID}/{orgID}")
    public ResponseEntity<?> getCount(@PathVariable Long workPlaceID, @PathVariable Long orgID) {
        return service.getCount(workPlaceID, orgID);
    }

    @PatchMapping("/register")
    public ResponseEntity<?> register(@RequestBody MissiveRegisteDTO missiveRegisteDTO) {
        return service.register(missiveRegisteDTO);
    }


}
