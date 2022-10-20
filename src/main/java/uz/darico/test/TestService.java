package uz.darico.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.darico.contentFile.ContentFile;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.feign.obj.WorkPlaceShortInfo;
import uz.darico.missive.Missive;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.OutReceiverService;
import uz.darico.signatory.Signatory;
import uz.darico.signatory.SignatoryService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 17/10/22, Mon, 10:12
 **/
@Service
public class TestService {
    @Autowired
    private SignatoryService signatoryService;
    @Autowired
    private WorkPlaceFeignService workPlaceFeignService;

    @Autowired
    private OutReceiverService outReceiverService;

    public ResponseEntity<?> test() {
        Signatory signatory = new Signatory(220L, 5L, 1);
        signatoryService.makePDFDTO(signatory);
        return ResponseEntity.ok(true);
    }
}
