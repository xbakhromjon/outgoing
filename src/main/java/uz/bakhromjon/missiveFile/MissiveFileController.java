package uz.bakhromjon.missiveFile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.bakhromjon.base.controller.AbstractController;

@RestController
@RequestMapping("/missive-file")
public class MissiveFileController extends AbstractController<MissiveFileService> {
    public MissiveFileController(MissiveFileService service) {
        super(service);
    }

}
