package uz.bakhromjon.fishka;

import lombok.*;
import uz.bakhromjon.base.entity.Auditable;
import uz.bakhromjon.contentFile.ContentFile;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 10/10/22, Mon, 09:36
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Fishka extends Auditable {
    @OneToOne
    private ContentFile file;
    private Integer fishkaTypeCode;
    private Boolean isVisible = true;
    private Long orgID;
}
