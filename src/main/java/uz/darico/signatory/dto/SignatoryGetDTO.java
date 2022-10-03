package uz.darico.signatory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 03/10/22, Mon, 17:17
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignatoryGetDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String status;
    private LocalDate statusTime;
}
