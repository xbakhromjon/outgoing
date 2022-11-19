import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.darico.confirmative.dto.ConfirmativePDFDTO;
import uz.darico.missive.MissiveService;
import uz.darico.missive.dto.PDFDTO;
import uz.darico.sender.dto.SenderPDFDTO;
import uz.darico.signatory.dto.SignatoryPDFDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 15/10/22, Sat, 17:15
 **/
@Component
public class PDFTest {
    @Autowired
    private MissiveService missiveService;

    @Test
    public void testPDF() {

    }
}
