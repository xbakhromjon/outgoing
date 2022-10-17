package uz.darico.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.darico.confirmative.dto.ConfirmativePDFDTO;
import uz.darico.sender.dto.SenderPDFDTO;
import uz.darico.signatory.dto.SignatoryPDFDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 15/10/22, Sat, 16:09
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PDFDTO {
    private String fishkaPath;
    private String date;
    private String number;
    private String content;
    private SignatoryPDFDTO signatoryPDFDTO;
    private SenderPDFDTO senderPDFDTO;
    private List<ConfirmativePDFDTO> confirmativePDFDTOs;
}
