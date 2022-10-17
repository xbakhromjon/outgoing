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
import uz.darico.signatory.SignatoryService;

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
        List<OutReceiver> outReceivers = List.of(new OutReceiver(220L, "xbakhromjontest@gmail.com", "xbakhromjontest@gmail.com"),
                new OutReceiver(220L, "xbakhromjontest@gmail.com", "xbakhromjontest@gmail.com"),
                new OutReceiver(220L, "xbakhromjontest@gmail.com", "xbakhromjontest@gmail.com"));
        Missive missive = new Missive();
        missive.setOutReceivers(outReceivers);
        ContentFile contentFile = new ContentFile();
        contentFile.setPath("/home/xbakhromjon/database/generated/87f5e254-f999-459a-8b54-bec9dc71c659.pdf");
        missive.setReadyPDF(contentFile);
        missive.setShortInfo("Ўзбекистон Республикаси Президентининг 2018 йил 19 февралдаги\n" +
                "“Ахборот технологиялари ва коммуникациялари соҳасини янада такомиллаштириш чора-тадбирлари тўғрисида”ги ПФ-5349-сон Фармонига мувофиқ “Ахборот-коммуникация технологияларини");
        outReceiverService.send(missive);
        return ResponseEntity.ok(true);
    }
}
