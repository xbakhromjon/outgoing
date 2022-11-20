package uz.bakhromjon.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.outReceiver.OutReceiverService;
import uz.bakhromjon.signatory.Signatory;
import uz.bakhromjon.signatory.SignatoryService;

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
