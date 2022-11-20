package uz.bakhromjon.confirmative.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 15/10/22, Sat, 16:13
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmativePDFDTO {
    private String fullPosition;
    private String qrCodePath;
    private String shortName;
}
